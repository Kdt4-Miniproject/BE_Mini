package org.vacation.back.service.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vacation.back.domain.Vacation;
import org.vacation.back.service.VacationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationMainResponseDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    @Transactional
    public void vacationSave(VacationSaveRequestDTO dto, HttpServletRequest request) {
        Member member = vacationRepository.findBymember(request.getAttribute("username").toString(), MemberStatus.ACTIVATION); // 써야해서 member 한번 조회 (로그인된 정보가지고)

        List<Vacation> vacationList = vacationRepository.findAllByDepartment(member.getDepartment().getDepartmentName()); // 조회한 member의 부서이름(ex)인사)가 승인된 연차 정보 리스트로 전부 불러옴
        LocalDate temp = dto.getStart(); // 현재 유저가 신청한 연자 시작 날짜
        LocalDate currentDate = LocalDate.now(); // 지금 localdate정보
        int lastDayOfMonth = currentDate.lengthOfMonth(); // 현재 달이 몇일까지 있는지 가져옴
        int arr[] = new int[lastDayOfMonth];

        for (int i = 0;i < vacationList.size();i++){
            LocalDate current = vacationList.get(i).getStart(); // 앞서 가져온 vacationlist의 인덱스 시작날짜
            while(!current.isAfter(vacationList.get(i).getEnd())){ // 시작날짜가 vacationlist의 인덱스 End보다 미래가 아닐때 ( 과거 or 같은날) 반복문 돌도록
                arr[current.getDayOfMonth()]++; // 현재 current가 위치하고 있는 일에 해당하는 arr의 값을 1씩 증가
                current = current.plusDays(1L); // while을 빠져나오기 위해 current를 하루씩 늘려줌
            }
        }

        while (!temp.isAfter(dto.getEnd())){ // 유저가 신청한 연차 시작날짜가 유저가 신청한 연차 끝나는날보다 미래가 아닐때  |||  ex) 유저가 01~05일 신청을 했을 경우 n일에 승인 된 연차가 몇개인지 확인해야함
            if(arr[temp.getDayOfMonth()] >= member.getDepartment().getVacationLimit()){ // 부서가 하루에 나갈 수 있는 휴가 제한의 수와 승인된 연차가 같거나 많을경우(많을리가 없긴함) throw exception 처리
                throw new AlreadyVacationException("보유 연차 초과");
            }
            temp = temp.plusDays(1L); // while을 빠져나오기 위해 temp를 하루씩 늘려줌
        }


        List<Vacation> vacationListByUserName = vacationRepository.findByVacationUserName((String) request.getAttribute("username")); // 현재 로그인 된 유저가 신청, 승인된 연차의 수를 가져오기 위해
        if (vacationListByUserName.size() >= Integer.valueOf(member.getPosition().getVacation())){ //  앞서 조회한 list의 사이즈가 직급별 휴가 제한
            throw new OveredVacationException("보유한 연차 초과");
        }
        for (Vacation vacation : vacationListByUserName) {
            if (request.getAttribute("username").equals(vacation.getMember().getUsername())
                    && dto.getStart().isEqual(vacation.getStart())) {
                throw new AlreadyVacationException("신청한 날짜에 존재하는 연차가 있습니다.");
            }
        }
            vacationRepository.save(dto.toEntity(member));

    }

    @Transactional
    public VacationResponseDTO vacationDetail(Long id) {
        Vacation vacation = vacationRepository.findByvacation(id).orElseThrow(NotFoundVacationException::new);
        VacationResponseDTO dto = VacationResponseDTO.toDTO(vacation);
        return dto;
    }


    @Override
    public List<VacationMainResponseDTO> vacationListMonth(String month) {

        List<Vacation> vacationList = vacationRepository.findAllByVacationMonth(Integer.valueOf(month));
        List<VacationMainResponseDTO> vacationResponseList = new ArrayList<>();
        for (Vacation vacation: vacationList) {
            VacationMainResponseDTO dto = new VacationMainResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setCreatedAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setPositionName(vacation.getMember().getPosition().getPositionName());
            dto.setStatus(vacation.getStatus());
            vacationResponseList.add(dto);
        }

        return vacationResponseList;
    }


    public Page<VacationResponseDTO> vacationListStatus(Pageable pageable) {

        Page<Vacation> vacationList = vacationRepository.findAllByVacationStatus(pageable);
        Page<VacationResponseDTO> vacationResponseList = vacationList.map(vacation -> {
            VacationResponseDTO dto = new VacationResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setEmployeeNumber(vacation.getMember().getEmployeeNumber());
            dto.setCreatedAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setPositionName(vacation.getMember().getPosition().getPositionName());
            dto.setStatus(vacation.getStatus());
            return dto;
        });
        return vacationResponseList;
    }

    public List<VacationMainResponseDTO> vacationMyList(HttpServletRequest request){
        List<Vacation> vacationList = vacationRepository.findAllByUserName(request.getAttribute("username").toString());

        List<VacationMainResponseDTO> vacationResponseList = new ArrayList<>();
        for (Vacation vacation: vacationList) {
            VacationMainResponseDTO dto = new VacationMainResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setCreatedAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setPositionName(vacation.getMember().getPosition().getPositionName());
            dto.setStatus(vacation.getStatus());
            vacationResponseList.add(dto);
        }

        return vacationResponseList;
    }



    @Transactional
    public void vacationModify(VacationModifyDTO dto) {
        Vacation vacation = vacationRepository.findById(dto.getId()).orElseThrow(NotFoundVacationException::new);
        vacation.modifyVacation(dto.getStart(), dto.getEnd());
    }

    @Transactional
    public void vacationDelete(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(NotFoundVacationException::new);
        if (vacation.getStatus() == VacationStatus.DELETED) {
            throw new AlreadyVacationException("이미 취소된 연차입니다.");
        }
        vacation.setStatus(VacationStatus.DELETED);
    }

    @Transactional
    public void vacationOk(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(NotFoundVacationException::new);
        if (vacation.getStatus() == VacationStatus.OK) {
            throw new AlreadyVacationException("이미 승인된 연차입니다.");
        }
        vacation.setStatus(VacationStatus.OK);
    }

    @Transactional
    public void vacationRejected(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(NotFoundVacationException::new);
        if (vacation.getStatus() == VacationStatus.REJECTED) {
            throw new AlreadyVacationException("이미 반려된 연차입니다.");
        }
        vacation.setStatus(VacationStatus.REJECTED);
    }


}

package org.vacation.back.service.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    private final MemberRepository memberRepository;
        // limit : 3          ||            start = 05-12 end = 05-14      date 0512 저장 0513 저장 0514저장

    @Transactional
    public void vacationSave(VacationSaveRequestDTO dto, HttpServletRequest request) {
        Member member = vacationRepository.findBymember((String) request.getAttribute("username"), MemberStatus.ACTIVATION);

        List<Vacation> vacationList = vacationRepository.findAllByDepartment(member.getDepartment().getDepartmentName());

        LocalDate temp = dto.getStart();
        while(temp.getDayOfMonth() <= dto.getEnd().getDayOfMonth()) { // 부서별 연차 횟수 조회 반복문
            int cnt = 0;
            for (int i = 0; i < vacationList.size(); i++) {
                LocalDate current = vacationList.get(i).getStart();
                while (!current.isAfter(vacationList.get(i).getEnd())) {
                    if (current.getDayOfMonth() == temp.getDayOfMonth()) {
                        cnt++;
                        break;
                    }
                    current = current.plusDays(1L);
                }
            }
            if (cnt >= member.getDepartment().getVacationLimit()) {
                throw new OveredVacationException("보유한 연차 초과");
            }
            temp = temp.plusDays(1L);
        }
        List<Vacation> vacationListByUserName = vacationRepository.findByVacationUserName((String) request.getAttribute("username"));
        if (vacationListByUserName.size() >= Integer.valueOf(member.getPosition().getVacation())){
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
    public Page<VacationResponseDTO> vacationListMonth(String month, Pageable pageable) {

        Page<Vacation> vacationList = vacationRepository.findAllByVacationMonth(Integer.valueOf(month), pageable);
        Page<VacationResponseDTO> vacationResponseList = vacationList.map(vacation -> {
            VacationResponseDTO dto = new VacationResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setCreateAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setStatus(vacation.getStatus());
            return dto;
        });

        return vacationResponseList;
    }


    public Page<VacationResponseDTO> vacationListStatus(Pageable pageable) {

        Page<Vacation> vacationList = vacationRepository.findAllByVacationStatus(VacationStatus.WAITING, pageable);
        Page<VacationResponseDTO> vacationResponseList = vacationList.map(vacation -> {
            VacationResponseDTO dto = new VacationResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setCreateAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setStatus(vacation.getStatus());
            return dto;
        });
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

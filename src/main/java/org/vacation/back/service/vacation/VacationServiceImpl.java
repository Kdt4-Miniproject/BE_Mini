package org.vacation.back.service.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    private final MemberRepository memberRepository;
        // limit : 3          ||            start = 05-12 end = 05-14      date 0512 저장 0513 저장 0514저장
    public void vacationSave(VacationSaveRequestDTO dto, HttpServletRequest request) {
        Member member = vacationRepository.findBymember((String) request.getAttribute("username"), MemberStatus.ACTIVATION);
        String vacation = String.valueOf(dto.getEnd().getDayOfMonth() - dto.getStart().getDayOfMonth());
        if (Integer.valueOf(member.getPosition().getVacation()) < Integer.valueOf(vacation)){
            throw new RuntimeException();
        }

        List<Vacation> vacationList = vacationRepository.findAllByDepartment(member.getDepartment().getDepartmentName());
        for (int i = 0; i < vacationList.size(); i++){
            if (request.getAttribute("username") == vacationList.get(i).getMember().getUsername()) {
                if(dto.getStart() == vacationList.get(i).getStart()){
                    throw new CommonException(ErrorCode.DUPLICATED_START, "신청한 연차 시작날짜가 이미 존재합니다.");
                }
            }
        }
        LocalDate temp = dto.getStart();
        while(temp.getDayOfMonth() <= dto.getEnd().getDayOfMonth()) {
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
                throw new RuntimeException("휴가 인원 초과");
            }
            temp = temp.plusDays(1L);
        }
        System.out.println("==========================================");
            vacationRepository.save(dto.toEntity(member));

    }

    @Transactional
    public VacationResponseDTO vacationDetail(Long id) {
        Vacation vacation = vacationRepository.findByvacation(id);
        VacationResponseDTO dto = VacationResponseDTO.toDTO(vacation);
        Member member = vacationRepository.findBymember(vacation.getMember().getUsername(), MemberStatus.ACTIVATION);
        if(dto == null) {
            throw new CommonException(ErrorCode.ID_NOT_FOUND, "해당 ID의 정보를 찾을 수 없습니다, ID를 확인하세요");
        }

        return dto;
    }

    public List<VacationResponseDTO> vacationListStatus() {

        List<Vacation> vacationList = vacationRepository.findAllByVacationStatus(VacationStatus.WAITING);
        List<VacationResponseDTO> vacationResponseList = new ArrayList<>();
        for (Vacation vacation : vacationList){
            VacationResponseDTO dto = new VacationResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setCreateAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setStatus(vacation.getStatus());
            vacationResponseList.add(dto);
        }
        return vacationResponseList;
    }

    public List<VacationResponseDTO> vacationListMonth(String month) {

        List<Vacation> vacationList = vacationRepository.findAllByVacationMonth(Integer.valueOf(month));
        List<VacationResponseDTO> vacationResponseList = new ArrayList<>();
        for (Vacation vacation : vacationList){
            VacationResponseDTO dto = new VacationResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMember().getName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setCreateAt(vacation.getCreatedAt());
            dto.setDepartmentName(vacation.getMember().getDepartment().getDepartmentName());
            dto.setStatus(vacation.getStatus());
            vacationResponseList.add(dto);
        }

        return vacationResponseList;
    }

    @Transactional
    public void vacationModify(VacationModifyDTO dto) {
        Vacation vacation = vacationRepository.findById(dto.getId()).orElseThrow(
                () -> new CommonException(ErrorCode.ID_NOT_FOUND,"해당 ID의 정보를 찾을 수 없습니다, ID를 확인하세요"));
        vacation.modifyVacation(dto.getStart(), dto.getEnd());
    }

    @Transactional
    public void vacationDelete(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.ID_NOT_FOUND,"해당 ID의 정보를 찾을 수 없습니다, ID를 확인하세요"));
        if (vacation.getStatus() == VacationStatus.DELETED) {
            throw new CommonException(ErrorCode.ALREADY_DELETED_VACATION, "이미 삭제된 연차입니다.");
        }
        vacation.setStatus(VacationStatus.DELETED);
    }

    @Transactional
    public void vacationOk(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.ID_NOT_FOUND,"해당 ID의 정보를 찾을 수 없습니다, ID를 확인하세요"));
        if (vacation.getStatus() == VacationStatus.OK) {
            throw new CommonException(ErrorCode.ALREADY_OK_VACATION, "이미 승인된 연차입니다.");
        }
        vacation.setStatus(VacationStatus.OK);
    }

    @Transactional
    public void vacationRejected(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.ID_NOT_FOUND,"해당 ID의 정보를 찾을 수 없습니다, ID를 확인하세요"));
        if (vacation.getStatus() == VacationStatus.REJECTED) {
            throw new CommonException(ErrorCode.ALREADY_REJECTED_VACATION, "이미 반려된 연차입니다.");
        }
        vacation.setStatus(VacationStatus.REJECTED);
    }
}

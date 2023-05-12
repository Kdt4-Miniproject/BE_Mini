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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;

    private final MemberRepository memberRepository;

    public void vacationSave(VacationSaveRequestDTO dto, HttpServletRequest request) {
        if (vacationRepository.findByVacationStart((String) request.getAttribute("username"), dto.getStart()) != null) {
            throw new CommonException(ErrorCode.DUPLICATED_START, "신청한 연차 시작날짜가 이미 존재합니다.");
        }

        Member member = memberRepository.findById((String) request.getAttribute("username")).orElseThrow(
                () -> new CommonException(ErrorCode.ID_NOT_FOUND,"해당 ID를 찾을 수 없습니다.")
        );

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

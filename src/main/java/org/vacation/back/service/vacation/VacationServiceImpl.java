package org.vacation.back.service.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Vacation;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.VacationService;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    public void vacationSave(VacationSaveRequestDTO dto) {
        try {
            vacationRepository.save(dto.toEntity());
        }catch (Exception e){
            throw new CommonException(ErrorCode.DUPLICATED_START, "신청한 연차 시작날짜가 이미 존재합니다.");
        }
    }

    public VacationResponseDTO vacationDetail(Long id) {
        VacationResponseDTO dto = VacationResponseDTO.toDTO(vacationRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.ID_NOT_FOUND,"해당 ID의 정보를 찾을 수 없습니다.")));
        return dto;
    }

    public List<VacationResponseDTO> vacationList() {
        List<Vacation> vacationList = vacationRepository.findAll();
        List<VacationResponseDTO> vacationResponseList = new ArrayList<>();
        for (Vacation vacation : vacationList){
            VacationResponseDTO dto = new VacationResponseDTO();
            dto.setId(vacation.getId());
            dto.setMemberName(vacation.getMemberName());
            dto.setStart(vacation.getStart());
            dto.setEnd(vacation.getEnd());
            dto.setStatus(vacation.getStatus());
            vacationResponseList.add(dto);
        }

        return vacationResponseList;
    }


    @Transactional
    public void vacationModify(Long id, VacationModifyDTO dto) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
        vacation.modifyVacation(dto.getStart(), dto.getEnd());
    }

    @Transactional
    public void vacationDelete(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
        vacation.setStatus(VacationStatus.DELETED);
    }

    @Transactional
    public void vacationOk(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
        vacation.setStatus(VacationStatus.OK);
    }

    @Transactional
    public void vacationRejected(Long id) {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(
                () -> new RuntimeException()
        );
        vacation.setStatus(VacationStatus.REJECTED);
    }
}

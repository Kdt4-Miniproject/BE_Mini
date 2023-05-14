package org.vacation.back.service.duty;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import org.vacation.back.dto.request.duty.DutyAssignDTO;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyResponseDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.service.DutyService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void dutySave(DutySaveRequestDTO dutySaveRequestDTO) {
        LocalDate currentDate = LocalDate.now();

        if (dutyRepository.findByDutyDay(dutySaveRequestDTO.getUsername(), dutySaveRequestDTO.getDay()) != null) {
            throw new CommonException(ErrorCode.EXIST_DUTY, "이미 당직이 존재합니다.");
        }

        if (dutySaveRequestDTO.getDay().isBefore(currentDate)) {
            throw new CommonException(ErrorCode.INVALID_DUTY_DATE, "이미 지난 날짜로 당직을 신청할 수 없습니다.");
        }

        Member member = memberRepository.findById(dutySaveRequestDTO.getUsername()).orElseThrow(
                () -> new CommonException(ErrorCode.ID_NOT_FOUND, "해당 ID를 찾을 수 없습니다.")
        );

        dutyRepository.save(dutySaveRequestDTO.toEntity(member));
    }


    @Transactional
    public DutyResponseDTO dutyDetail(Long id) {

        Duty duty = dutyRepository.findByDuty(id);
        DutyResponseDTO dutyResponseDTO = DutyResponseDTO.toDTO(duty);
        Member member = dutyRepository.findByMember(duty.getMember().getUsername(), MemberStatus.ACTIVATION);

        if (dutyResponseDTO == null) {
            throw new CommonException(ErrorCode.NOTFOUND_ID, "ID를 찾을 수 없습니다.");
        }
        return dutyResponseDTO;
    }

    public List<DutyResponseDTO> dutyListStatus() {
        List<Duty> dutyList = dutyRepository.findAllByDutyStatus();
        if (dutyList == null) {
            throw new CommonException(ErrorCode.NO_RESULT, "당직을 신청한 사람이 없습니다.");
        }

        List<DutyResponseDTO> dutyResponseDTOList = new ArrayList<>();

        for (Duty duty : dutyList) {
            DutyResponseDTO dutyResponseDTO = new DutyResponseDTO();
            dutyResponseDTO.setId(duty.getId());
            dutyResponseDTO.setMemberName(duty.getMember().getName());
            dutyResponseDTO.setDay(duty.getDay());
            dutyResponseDTO.setDepartmentName(duty.getMember().getDepartment().getDepartmentName());
            dutyResponseDTO.setStatus(duty.getStatus());
            dutyResponseDTO.setCreatedAt(duty.getCreatedAt());
            dutyResponseDTOList.add(dutyResponseDTO);
        }

        return dutyResponseDTOList;
    }

    public List<DutyResponseDTO> dutyListMonth(String month) {
        List<Duty> dutyList = dutyRepository.findAllByDutyMonth(Integer.valueOf(month));
        if (dutyList == null) {
            throw new CommonException(ErrorCode.NO_RESULT, "당직을 신청한 사람이 없습니다.");
        }

        List<DutyResponseDTO> dutyResponseDTOList = new ArrayList<>();

        for (Duty duty : dutyList) {
            DutyResponseDTO dutyResponseDTO = new DutyResponseDTO();
            dutyResponseDTO.setId(duty.getId());
            dutyResponseDTO.setMemberName(duty.getMember().getName());
            dutyResponseDTO.setDay(duty.getDay());
            dutyResponseDTO.setDepartmentName(duty.getMember().getDepartment().getDepartmentName());
            dutyResponseDTO.setStatus(duty.getStatus());
            dutyResponseDTO.setCreatedAt(duty.getCreatedAt());
            dutyResponseDTOList.add(dutyResponseDTO);
        }

        return dutyResponseDTOList;
    }

    @Transactional
    public void dutyModify(DutyModifyDTO dutyModifyDTO) {
        Duty duty = dutyRepository.findById(dutyModifyDTO.getId()).orElseThrow(
                () -> new CommonException(ErrorCode.NOTFOUND_ID, "ID를 찾을 수 없습니다."));

        DutyStatus status = dutyRepository.findByDay(dutyModifyDTO.getDay()).getStatus();
        if(status == DutyStatus.UPDATE_WAITING){
            throw new CommonException(ErrorCode.DUPLICATED_UPDATE, "이미 수정요청이 있습니다.");
        }
        LocalDate currentDay = duty.getDay();
        LocalDate wantedDay = dutyModifyDTO.getDay();

        Duty duty2 = dutyRepository.findByDay(wantedDay);
        if(duty2 == null){
            duty.modifyDuty(wantedDay);
            duty.setStatus(DutyStatus.WAITING);
            // 더티 체킹으로 save 사용 안함
        }else {
            duty.modifyDuty(wantedDay);
            duty.setStatus(DutyStatus.UPDATE_WAITING);
            duty.setOriginalDay(currentDay);

            duty2.modifyDuty(currentDay);
            duty2.setStatus(DutyStatus.UPDATE_WAITING);
            duty2.setOriginalDay(wantedDay);
        }
    }

    @Transactional
    public void dutyDelete(Long id) {
        Duty duty = dutyRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.NOTFOUND_ID, "ID를 찾을 수 없습니다."));

        if (duty.getStatus() == DutyStatus.DELETED) {
            throw new CommonException(ErrorCode.ALREADY_DELETED_DUTY, "이미 삭제된 당직입니다.");
        }

        duty.setStatus(DutyStatus.DELETED);

    }

    @Transactional
    public void dutyOk(Long id) {
        Duty duty = dutyRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.NOTFOUND_ID, "ID를 찾을 수 없습니다."));

        if (duty.getStatus() == DutyStatus.DELETED) {
            throw new CommonException(ErrorCode.ALREADY_DELETED_DUTY, "이미 삭제된 당직입니다.");
        } else if (duty.getStatus() == DutyStatus.OK) {
            throw new CommonException(ErrorCode.ALREADY_OK_DUTY, "이미 승인된 당직입니다.");
        } else {

            duty.setStatus(DutyStatus.OK);

        }
    }

    @Transactional
    public void dutyRejected(Long id) {
        Duty duty = dutyRepository.findById(id).orElseThrow(
                () -> new CommonException(ErrorCode.NOTFOUND_ID, "ID를 찾을 수 없습니다."));

        if (duty.getStatus() == DutyStatus.DELETED) {
            throw new CommonException(ErrorCode.ALREADY_DELETED_DUTY, "이미 삭제된 당직입니다.");
        } else if (duty.getStatus() == DutyStatus.REJECTED) {
            throw new CommonException(ErrorCode.ALREADY_REJECTED_DUTY, "이미 거절된 당직입니다.");
        } else if(duty.getStatus() == DutyStatus.UPDATE_WAITING){

            duty.setDay(duty.getOriginalDay());
            duty.setStatus(DutyStatus.OK);

        }else{
            duty.setStatus(DutyStatus.REJECTED);
        }

    }


    public List<DutyResponseDTO> dutyAssign() {

        // 현재 날짜를 가져옴
        LocalDate currentDate = LocalDate.now();

        // 현재 달의 시작일과 마지막일 설정
        LocalDate startDate = currentDate.withDayOfMonth(1);
        LocalDate endDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        Map<LocalDate, Integer> dateValueMap = new HashMap<>();
        int value = 1;

        while (!startDate.isAfter(endDate)) {
            dateValueMap.put(startDate, value);
            startDate = startDate.plusDays(1);
            value++;
        }

        return null;
    }


}

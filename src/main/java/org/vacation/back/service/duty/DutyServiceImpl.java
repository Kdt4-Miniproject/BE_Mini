package org.vacation.back.service.duty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyMainResponseDTO;
import org.vacation.back.dto.response.DutyResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.service.DutyService;
import org.vacation.back.utils.AssignUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DutyServiceImpl implements DutyService {
    private final DutyRepository dutyRepository;

    private final MemberRepository memberRepository;

    private final AssignUtils assignUtils;




    @Transactional
    public void dutySave(DutySaveRequestDTO dutySaveRequestDTO) {

        Member member = dutyRepository.findByMember(dutySaveRequestDTO.getUsername(), MemberStatus.ACTIVATION);
        if (member == null) {
            throw new DutyMemberNotFoundException("회원을 찾을 수 없습니다.");
        }

        LocalDate currentDate = LocalDate.now();

        if (dutySaveRequestDTO.getDay().isBefore(currentDate)) {
            throw new PastDateForDutyException("이미 지난 날짜로 당직을 신청할 수 없습니다.");
        }

        Duty duty = dutyRepository.findByDayStatus(dutySaveRequestDTO.getDay());
            if (duty != null) {
                throw new AlreadyDutyException("이미 당직이 존재합니다.");
            }
        dutyRepository.save(dutySaveRequestDTO.toEntity(member));
    }



    @Transactional
    public DutyResponseDTO dutyDetail(Long id) {

        Duty duty = dutyRepository.findByDuty(id).orElseThrow(NotFoundDutyException::new);
        DutyResponseDTO dutyResponseDTO = DutyResponseDTO.toDTO(duty);

        return dutyResponseDTO;
    }

    public List<DutyMainResponseDTO> dutyListMonth(String month) {
        List<Duty> dutyList = dutyRepository.findAllByDutyMonth(Integer.valueOf(month));
        List<DutyMainResponseDTO> dutyMainResponseList = new ArrayList<>();
        for(Duty duty : dutyList){
            DutyMainResponseDTO dutyMainResponseDTO = new DutyMainResponseDTO();

            dutyMainResponseDTO.setId(duty.getId());
            dutyMainResponseDTO.setMemberName(duty.getMember().getName());
            dutyMainResponseDTO.setDay(duty.getDay());
            dutyMainResponseDTO.setDepartmentName(duty.getMember().getDepartment().getDepartmentName());
            dutyMainResponseDTO.setStatus(duty.getStatus());
            dutyMainResponseDTO.setCreatedAt(duty.getCreatedAt());
            dutyMainResponseDTO.setPositionName(duty.getMember().getPosition().getPositionName());
            dutyMainResponseList.add(dutyMainResponseDTO);

        }

        return dutyMainResponseList;
    }

    public Page<DutyResponseDTO> dutyListStatus(Pageable pageable) {
        Page<Duty> dutyList =  dutyRepository.findAllByDutyStatus(pageable);
        Page<DutyResponseDTO> dutyResponseList = dutyList.map(duty -> {

            DutyResponseDTO dutyResponseDTO = new DutyResponseDTO();

            dutyResponseDTO.setId(duty.getId());
            dutyResponseDTO.setMemberName(duty.getMember().getName());
            dutyResponseDTO.setDay(duty.getDay());
            dutyResponseDTO.setDepartmentName(duty.getMember().getDepartment().getDepartmentName());
            dutyResponseDTO.setStatus(duty.getStatus());
            dutyResponseDTO.setCreatedAt(duty.getCreatedAt());
            dutyResponseDTO.setPositionName(duty.getMember().getPosition().getPositionName());
            dutyResponseDTO.setEmployeeNumber(duty.getMember().getEmployeeNumber());
            return dutyResponseDTO;
        });

        return dutyResponseList;
    }

    public List<DutyMainResponseDTO> dutyMyList(HttpServletRequest request){
        List<Duty> dutyList = dutyRepository.findAllByUserName(request.getAttribute("username").toString());

        List<DutyMainResponseDTO> dutyMainResponseList = new ArrayList<>();
        for(Duty duty : dutyList){
            DutyMainResponseDTO dutyMainResponseDTO = new DutyMainResponseDTO();
            dutyMainResponseDTO.setId(duty.getId());
            dutyMainResponseDTO.setMemberName(duty.getMember().getName());
            dutyMainResponseDTO.setDay(duty.getDay());
            dutyMainResponseDTO.setDepartmentName(duty.getMember().getDepartment().getDepartmentName());
            dutyMainResponseDTO.setStatus(duty.getStatus());
            dutyMainResponseDTO.setCreatedAt(duty.getCreatedAt());
            dutyMainResponseDTO.setPositionName(duty.getMember().getPosition().getPositionName());
            dutyMainResponseList.add(dutyMainResponseDTO);
        }
        return dutyMainResponseList;
    }


    @Transactional
    public void dutyModify(DutyModifyDTO dutyModifyDTO) {
        Duty duty = dutyRepository.findById(dutyModifyDTO.getId()).orElseThrow(NotFoundDutyException::new);

        LocalDate currentDay = duty.getDay();
        LocalDate wantedDay = dutyModifyDTO.getDay();

        if(currentDay.equals(wantedDay)) {
            throw new SameDayException("같은 날짜로 수정을 요청하였습니다.");
        }else if(duty.getStatus() == DutyStatus.DELETED) {
            throw new AlreadyDeletedException("이미 삭제된 당직입니다.");
        }else if (duty.getStatus() == DutyStatus.REJECTED) {
            throw new AlreadyRejectedException("이미 거절된 당직입니다.");
        }else if (duty.getStatus() == DutyStatus.OK) {
            throw new AlreadyOkException("이미 승인된 당직입니다.");
        }

        Duty duty2 = dutyRepository.findByDay(wantedDay);
        if(duty2 == null){
            duty.modifyDuty(wantedDay);
            duty.setStatus(DutyStatus.WAITING);
            // 더티 체킹으로 save 사용 안함
        }else if(duty2.getStatus() == DutyStatus.UPDATE_WAITING) {

            throw new AlreadyModifyException("이미 수정요청된 당직 신청입니다.");

        }else{

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
        Duty duty = dutyRepository.findById(id).orElseThrow(NotFoundDutyException::new);

        LocalDate currentDay = duty.getDay();
        LocalDate originalDay = duty.getOriginalDay();

        if (duty.getStatus() == DutyStatus.DELETED) {
            throw new AlreadyDeletedException("이미 삭제된 당직입니다.");
        }else if (duty.getStatus() == DutyStatus.UPDATE_WAITING) {
            duty.setStatus(DutyStatus.DELETED);
            Duty existingDuty = dutyRepository.findByDayAndOk(originalDay, DutyStatus.UPDATE_WAITING);
            existingDuty.setStatus(DutyStatus.DELETED);
        }else {
            duty.setStatus(DutyStatus.DELETED);
        }
    }

    @Transactional
    public void dutyOk(Long id) {
        Duty duty = dutyRepository.findById(id).orElseThrow(NotFoundDutyException::new);

        LocalDate currentDay = duty.getDay();
        LocalDate originalDay = duty.getOriginalDay();

        if (duty.getStatus() == DutyStatus.DELETED) {
            throw new AlreadyDeletedException("이미 삭제된 당직입니다.");
        } else if(duty.getStatus() == DutyStatus.WAITING){
            duty.setStatus(DutyStatus.OK);
        } else if (duty.getStatus() == DutyStatus.OK) {
            Duty okDuty = dutyRepository.findByDayAndOk(currentDay, DutyStatus.OK);

            if (okDuty != null) {
                throw new DuplicatedDutyException("동일한 날짜에 이미 당직이 지정되어 있습니다.");
            } else {
                duty.setStatus(DutyStatus.OK);
            }
        }else if (duty.getStatus() == DutyStatus.UPDATE_WAITING) {
            duty.setStatus(DutyStatus.OK);
            Duty existingDuty = dutyRepository.findByDayAndOk(originalDay, DutyStatus.UPDATE_WAITING);
            existingDuty.setStatus(DutyStatus.OK);
        }else if(duty.getStatus() == DutyStatus.REJECTED){
            Duty rejetedDuty = dutyRepository.findByDayAndOk(currentDay, DutyStatus.OK);
            if (rejetedDuty != null) {
                throw new DuplicatedDutyException("동일한 날짜에 이미 당직이 지정되어 있습니다.");
            } else {
                duty.setStatus(DutyStatus.OK);
            }

        }else{
            duty.setStatus(DutyStatus.OK);
        }
    }

    @Transactional
    public void dutyRejected(Long id) {
        Duty duty = dutyRepository.findById(id).orElseThrow(NotFoundDutyException::new);
        LocalDate originalDay = duty.getOriginalDay();

        if (duty.getStatus() == DutyStatus.DELETED) {
            throw new AlreadyDeletedException("이미 삭제된 당직입니다.");
        }else if (duty.getStatus() == DutyStatus.REJECTED) {
            throw new AlreadyRejectedException("이미 거절된 당직입니다.");
        }else if (duty.getStatus() == DutyStatus.WAITING){
            duty.setStatus(DutyStatus.REJECTED);
        }else if (duty.getStatus() == DutyStatus.UPDATE_WAITING) {
            duty.setStatus(DutyStatus.REJECTED);
            Duty existingDuty = dutyRepository.findByDayAndOk(originalDay, DutyStatus.UPDATE_WAITING);
            existingDuty.setStatus(DutyStatus.REJECTED);
        }else{
            duty.setStatus(DutyStatus.REJECTED);
        }

        }



    @Transactional
    public void dutyAssign(){

        List<Duty> duty = dutyRepository.findAll();
        if (duty.isEmpty()) {
            assignUtils.assign();

        }else{
            throw new IntialDutyException("없음");
        }
    }

}

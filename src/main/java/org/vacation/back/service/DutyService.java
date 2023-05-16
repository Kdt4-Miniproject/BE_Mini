package org.vacation.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vacation.back.domain.Duty;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface DutyService {


    public List<DutyResponseDTO> findAllOk();
    public void dutySave(DutySaveRequestDTO dutySaveRequestDTO);

    public DutyResponseDTO dutyDetail(Long id);

    public Page<DutyResponseDTO> dutyListStatus(Pageable pageable);

    public List<DutyResponseDTO> dutyListMonth(String month);

    public void dutyModify(DutyModifyDTO dutyModifyDTO);

    public void dutyDelete(Long id);

    public void dutyOk(Long id);

    public void dutyRejected(Long id);
    public void dutyAssign();


}

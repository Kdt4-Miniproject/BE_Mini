package org.vacation.back.service;

import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyResponseDTO;

import java.util.List;


public interface DutyService {


    public void dutySave(DutySaveRequestDTO dutySaveRequestDTO);

    public DutyResponseDTO dutyDetail(Long id);

    public List<DutyResponseDTO> dutyListStatus();

    public List<DutyResponseDTO> dutyListMonth(String month);

    public void dutyModify(DutyModifyDTO dutyModifyDTO);

    public void dutyDelete(Long id);

    public void dutyOk(Long id);

    public void dutyRejected(Long id);

    public List<DutyResponseDTO> dutyAssign();


}

package org.vacation.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface DutyService {


    public void dutySave(DutySaveRequestDTO dutySaveRequestDTO, HttpServletRequest request);

    public DutyResponseDTO dutyDetail(Long id);

    public Page<DutyResponseDTO> dutyListStatus(Pageable pageable);

    public Page<DutyResponseDTO> dutyListMonth(String month, Pageable pageable);

    public void dutyModify(DutyModifyDTO dutyModifyDTO);

    public void dutyDelete(Long id);

    public void dutyOk(Long id);

    public void dutyRejected(Long id);


}

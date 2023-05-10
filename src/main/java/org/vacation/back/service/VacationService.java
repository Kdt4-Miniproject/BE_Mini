package org.vacation.back.service;

import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationResponseDTO;

import java.util.List;

public interface VacationService {

    public void vacationSave(VacationSaveRequestDTO dto, String userName);

    public VacationResponseDTO vacationDetail(Long id);

    public List<VacationResponseDTO> vacationListMonth(String month);

    public List<VacationResponseDTO> vacationListStatus();

    public void vacationModify(Long id, VacationModifyDTO dto);

    public void vacationDelete(Long id);

    public void vacationOk(Long id);

    public void vacationRejected(Long id);
}

package org.vacation.back.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationMainResponseDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface VacationService {


    public void vacationSave(VacationSaveRequestDTO dto, HttpServletRequest request);

    public VacationResponseDTO vacationDetail(Long id);

    public List<VacationMainResponseDTO> vacationListMonth(String month);

    public Page<VacationResponseDTO> vacationListStatus(Pageable pageable);

    public void vacationModify(VacationModifyDTO dto);


    public void vacationDelete(Long id);

    public void vacationOk(Long id);

    public void vacationRejected(Long id);
}

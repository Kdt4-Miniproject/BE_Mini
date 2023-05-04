package org.vacation.back.service;

import org.vacation.back.dto.common.VacationDTO;

import java.util.List;

public interface VacationService {

    public void vacationSave();

    public VacationDTO vacationDetail(Long id);

    public List<VacationDTO> vacationList();

    public void vacationModify(Long id);

    public void vacationDelete(Long id);

    public void vacationOk(Long id);

    public void vacationRejected(Long id);
}

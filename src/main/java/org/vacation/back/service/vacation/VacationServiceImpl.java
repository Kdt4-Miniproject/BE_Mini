package org.vacation.back.service.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vacation.back.domain.Vacation;
import org.vacation.back.dto.common.VacationDTO;
import org.vacation.back.repository.VacationTempRepository;
import org.vacation.back.service.VacationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService {

    private final VacationTempRepository vacationRepository;


    @Override
    public void vacationSave() {

    }

    @Override
    public Vacation vacationDetail(Long id) {
        return null;
    }

    @Override
    public List<VacationDTO> vacationList() {
        return null;
    }

    @Override
    public void vacationModify(Long id) {

    }

    @Override
    public void vacationDelete(Long id) {

    }

    @Override
    public void vacationOk(Long id) {

    }

    @Override
    public void vacationRejected(Long id) {

    }
}

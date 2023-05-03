package org.vacation.back.service.vacation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vacation.back.domain.VacationTemp;
import org.vacation.back.dto.common.VacationTempDTO;
import org.vacation.back.repository.VacationTempRepository;
import org.vacation.back.service.VacationTempService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationTempServiceImpl implements VacationTempService {

    private final VacationTempRepository vacationRepository;


}

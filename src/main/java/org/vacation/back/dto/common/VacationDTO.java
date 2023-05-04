package org.vacation.back.dto.common;

import org.vacation.back.common.VacationStatus;

import java.time.LocalDate;

public class VacationDTO {

    private Long id;

    private LocalDate start;

    private LocalDate end;

    private VacationStatus status;
}

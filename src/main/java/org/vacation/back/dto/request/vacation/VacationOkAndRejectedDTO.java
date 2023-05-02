package org.vacation.back.dto.request.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class VacationOkAndRejectedDTO{

    VacationStatus status;
}

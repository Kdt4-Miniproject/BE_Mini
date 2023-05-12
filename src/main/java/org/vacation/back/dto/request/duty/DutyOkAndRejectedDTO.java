package org.vacation.back.dto.request.duty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DutyStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DutyOkAndRejectedDTO {

    private Long id;
    private DutyStatus status;
}
package org.vacation.back.dto.request.duty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.VacationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutySaveRequestDTO {
    private String username;

    private String day;

    private boolean deleted;

    private DutyStatus status;
}

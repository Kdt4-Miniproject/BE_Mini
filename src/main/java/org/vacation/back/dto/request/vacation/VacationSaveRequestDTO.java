package org.vacation.back.dto.request.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacationSaveRequestDTO {

    String username;

    String start;

    String end;

    boolean deleted;

    VacationStatus status;
}

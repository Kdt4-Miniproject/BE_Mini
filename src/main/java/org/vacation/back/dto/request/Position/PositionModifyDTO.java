package org.vacation.back.dto.request.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.PositionStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionModifyDTO {

    private String positionName;

    private String vacation;

    private PositionStatus status;
}

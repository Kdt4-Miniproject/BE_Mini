package org.vacation.back.dto.request.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.domain.Position;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionSaveDTO {

    private String positionName;

    private String vacation;

    public Position toEntity() {
        return Position.builder()
                .positionName(this.positionName)
                .vacation(this.vacation)
                .status(PositionStatus.ACTIVATION)
                .build();
    }

}

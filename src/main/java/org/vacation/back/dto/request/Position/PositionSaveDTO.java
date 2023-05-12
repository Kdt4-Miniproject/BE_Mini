package org.vacation.back.dto.request.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.domain.Position;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionSaveDTO {

    @NotEmpty
    private String positionName;

    @NotEmpty
    private String vacation;

    public Position toEntity(){
        return Position.builder()
                .positionName(this.positionName)
                .vacation(this.vacation)
                .status(PositionStatus.ACTIVATION)
                .build();
    }

}

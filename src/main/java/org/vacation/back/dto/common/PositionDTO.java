package org.vacation.back.dto.common;

import lombok.*;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.domain.Position;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionDTO {

    private String positionName;

    private String vacation;

    private PositionStatus status;

    public static PositionDTO toDTO(Position position) {
        return PositionDTO.builder()
                .positionName(position.getPositionName())
                .vacation(position.getVacation())
                .status(position.getStatus())
                .build();
    }

    public PositionDTO(Position position) {
        this.positionName = position.getPositionName();
        this.vacation = position.getVacation();
        this.status = position.getStatus();
    }
}

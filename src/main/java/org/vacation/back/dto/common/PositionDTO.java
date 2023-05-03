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

    private PositionStatus position;

    private String vacation;

    private boolean deleted;

    public static PositionDTO toDTO(Position position) {
        return PositionDTO.builder()
                .position(position.getPosition())
                .vacation(position.getVacation())
                .deleted(position.isDeleted())
                .build();
    }
}

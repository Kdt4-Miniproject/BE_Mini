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

    private Long id;

    private PositionStatus position;

    private String vacation;

    private String years;

    public static PositionDTO toDTO(Position position) {
        return PositionDTO.builder()

                .build();
    }
}

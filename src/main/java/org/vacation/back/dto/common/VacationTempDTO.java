package org.vacation.back.dto.common;

import lombok.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Vacation;

import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacationTempDTO {

    Long id;


    String start;

    String end;

    boolean deleted;

    VacationStatus status;


    public static VacationTempDTO toDTO(Vacation vacation){
        return VacationTempDTO.builder()
                .id(vacation.getId())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .build();
    }
}

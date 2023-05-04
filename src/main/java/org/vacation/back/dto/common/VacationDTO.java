package org.vacation.back.dto.common;

import lombok.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Vacation;
import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacationDTO {

    private Long id;

    private LocalDate start;

    private LocalDate end;

    private boolean deleted;

    private VacationStatus status;


    public static VacationDTO toDTO(Vacation vacation){
        return VacationDTO.builder()
                .id(vacation.getId())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .build();
    }
}

package org.vacation.back.dto.response;

import lombok.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VacationResponseDTO {

    private Long id;

    private String memberName;

    private LocalDate start;

    private LocalDate end;

    private VacationStatus status;

    public static VacationResponseDTO toDTO(Vacation vacation){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(vacation.getMemberName())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .build();
    }
}

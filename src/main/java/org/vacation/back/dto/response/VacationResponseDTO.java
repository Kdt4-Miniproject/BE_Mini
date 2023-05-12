package org.vacation.back.dto.response;

import lombok.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Department;
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

    private Department department;

    //부서 추가

    public static VacationResponseDTO toDTO(Vacation vacation){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(vacation.getMember().getName())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .department(vacation.getMember().getDepartment())
                .build();
    }
    public static VacationResponseDTO toDTOv(Vacation vacation){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(vacation.getMember().getName())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .build();
    }
}

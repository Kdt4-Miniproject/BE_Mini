package org.vacation.back.dto.response;

import lombok.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

    private VacationStatus status;

    private String departmentName;

    private String positionName;



    //부서 추가

    public static VacationResponseDTO toDTO(Vacation vacation){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(vacation.getMember().getName())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .createdAt(vacation.getCreatedAt())
                .departmentName(vacation.getMember().getDepartment().getDepartmentName())
                .positionName(vacation.getMember().getPosition().getPositionName())
                .build();
    }
    public static VacationResponseDTO toDTOv(Vacation vacation,String departmentName,String positionName){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(vacation.getMember().getName())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .departmentName(departmentName)
                .positionName(positionName)
                .createdAt(vacation.getCreatedAt())
                .build();
    }
}

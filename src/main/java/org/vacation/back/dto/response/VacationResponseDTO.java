package org.vacation.back.dto.response;

import lombok.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Member;
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

    private String employeeNumber;
    private LocalDate start;

    private LocalDate end;

    private LocalDateTime createdAt;

    private VacationStatus status;

    private String departmentName;

    private String positionName;

    private String employeeNumber;



    //부서 추가

    public static VacationResponseDTO toDTO(Vacation vacation){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(vacation.getMember().getName())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .employeeNumber(vacation.getMember().getEmployeeNumber())
                .createdAt(vacation.getCreatedAt())
                .departmentName(vacation.getMember().getDepartment().getDepartmentName())
                .positionName(vacation.getMember().getPosition().getPositionName())
                .build();
    }
    public static VacationResponseDTO toDTOv(Vacation vacation,Member member,String departmentName,String positionName){
        return VacationResponseDTO.builder()
                .id(vacation.getId())
                .memberName(member.getName())
                .employeeNumber(member.getEmployeeNumber())
                .start(vacation.getStart())
                .end(vacation.getEnd())
                .status(vacation.getStatus())
                .departmentName(departmentName)
                .positionName(positionName)
                .createdAt(vacation.getCreatedAt())
                .build();
    }
}

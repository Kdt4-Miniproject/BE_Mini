package org.vacation.back.dto.common;

import lombok.*;
import org.vacation.back.common.DepartmentStatus;
import org.vacation.back.domain.Department;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {

    private String departmentName;

    private String vacationLimit;

    private String departmentPersonal;

    private DepartmentStatus status;

    public static DepartmentDTO toDTO(Department department){
        return DepartmentDTO.builder()
                .build();
    }

}

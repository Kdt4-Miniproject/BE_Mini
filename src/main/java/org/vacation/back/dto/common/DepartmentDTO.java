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

    private Integer vacationLimit;

    private Integer departmentPersonal;

    private DepartmentStatus status;

    public static DepartmentDTO toDTO(Department department){
        return DepartmentDTO.builder()
                .departmentName(department.getDepartmentName())
                .vacationLimit(department.getVacationLimit())
                .departmentPersonal(department.getDepartmentPersonal())
                .status(department.getStatus())
                .build();
    }

    public DepartmentDTO(Department department) {
        this.departmentName = department.getDepartmentName();
        this.vacationLimit = department.getVacationLimit();
        this.departmentPersonal = department.getDepartmentPersonal();
        this.status = department.getStatus();
    }

}

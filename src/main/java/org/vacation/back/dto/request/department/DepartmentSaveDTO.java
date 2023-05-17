package org.vacation.back.dto.request.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DepartmentStatus;
import org.vacation.back.domain.Department;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSaveDTO {

    private String departmentName;

    private String vacationLimit;

    public Department toEntity() {
        return Department.builder()
                .departmentName(this.departmentName)
                .vacationLimit(Integer.valueOf(this.vacationLimit))
                .departmentPersonal(0)
                .status(DepartmentStatus.ACTIVATION)
                .build();
    }

}


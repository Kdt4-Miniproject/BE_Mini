package org.vacation.back.dto.request.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DepartmentStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentModifyDTO {

    private String departmentName;

    private String vacationLimit;

    private String departmentPersonal;

    private DepartmentStatus status;
}

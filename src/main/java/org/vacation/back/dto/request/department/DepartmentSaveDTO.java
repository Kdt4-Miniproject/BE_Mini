package org.vacation.back.dto.request.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.vacation.back.common.DepartmentStatus;
import org.vacation.back.domain.Department;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSaveDTO {

    @NotEmpty
    private String departmentName;

    //    @Pattern(regexp = "^[0-9]{1,2}$", message = "1 ~ 2자리 숫자만 입력해주세요")
    @Range(min = 1, max = 10)
    @NotNull
    private Integer vacationLimit;

    //    @Pattern(regexp = "^[0-9]{1,2}$", message = "1 ~ 2자리 숫자만 입력해주세요")
    @Range(min = 1, max = 10)
    @NotNull
    private Integer departmentPersonal;

    public Department toEntity() {
        return Department.builder()
                .departmentName(this.departmentName)
                .vacationLimit(this.vacationLimit)
                .departmentPersonal(this.departmentPersonal)
                .status(DepartmentStatus.ACTIVATION)
                .build();
    }

}


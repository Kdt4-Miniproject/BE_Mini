package org.vacation.back.dto.request.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentModifyDTO {

    //    @Pattern(regexp = "^[0-9]{1,2}$", message = "1 ~ 2자리 숫자만 입력해주세요")
    @Range(min = 1, max = 10)
    @NotNull
    private Integer vacationLimit;

    //    @Pattern(regexp = "^[0-9]{1,2}$", message = "1 ~ 2자리 숫자만 입력해주세요")
    @Range(min = 1, max = 10)
    @NotNull
    private Integer departmentPersonal;

}

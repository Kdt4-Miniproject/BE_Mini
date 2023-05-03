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

    private DepartmentStatus department;

    private String vacation_limit; // 부서별 휴가 최대 인원

    private String personal; // 총원
}

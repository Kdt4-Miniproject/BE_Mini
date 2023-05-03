package org.vacation.back.dto.common;

import lombok.*;
import org.vacation.back.common.DepartmentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {

    private DepartmentStatus department;

    private String vacation_limit; // 부서별 휴가 최대 인원

    private String personal; // 총원

    private boolean deleted;

}

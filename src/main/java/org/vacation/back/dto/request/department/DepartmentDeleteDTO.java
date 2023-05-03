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
public class DepartmentDeleteDTO {

    private DepartmentStatus department;

    private boolean deleted; // 삭제 : true
}

package org.vacation.back.domain.id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Position;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionAndDepartmentId  implements Serializable {
        private String positionName;
        private String departmentName;

}

package org.vacation.back.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.vacation.back.domain.id.PositionAndDepartmentId;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(PositionAndDepartmentId.class)
public class PositionAndDepartment {
    @Id
    @ManyToOne
    @JoinColumn(name = "position_name")
    private Position positionName;

    @Id
    @ManyToOne
    @JoinColumn(name = "department_name")
    private Department departmentName;
}

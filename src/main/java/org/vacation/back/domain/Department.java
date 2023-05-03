package org.vacation.back.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Department {

    @Id
    private String departmentName;

    private Integer vacationLimit; //휴가인원

    private Integer departmentPersonal; //부서총인원

    @OneToMany(mappedBy = "department")
    @Builder.Default
    private List<Member> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "departmentName")
    @Builder.Default
    private List<PositionAndDepartment> positionAndDepartments = new ArrayList<>();
}

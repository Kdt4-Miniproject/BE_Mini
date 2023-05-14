package org.vacation.back.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DepartmentStatus;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private DepartmentStatus status;

    @OneToMany(mappedBy = "department")
    @Builder.Default
    private List<Member> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "departmentName")
    @Builder.Default
    private List<PositionAndDepartment> positionAndDepartments = new ArrayList<>();

    public void modify(Integer vacationLimit, Integer departmentPersonal){
        this.vacationLimit = vacationLimit;
        this.departmentPersonal = departmentPersonal;
    }

    public void setStatus(DepartmentStatus status) {
        this.status = status;
    }

    public void plusPersonal(){
        this.departmentPersonal = this.departmentPersonal + 1;
    }
    public void minusPersonal(){
        this.departmentPersonal = this.departmentPersonal - 1;
    }
}

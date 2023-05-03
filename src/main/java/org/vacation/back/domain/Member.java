package org.vacation.back.domain;


import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;
import org.vacation.back.common.MemberStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "deleted = false")
public class Member extends BaseEntity {


    @Id
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING) private Role role;


    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String birthdate;

    private Integer totalYears;

    private Integer years;


    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    private String name;

    private boolean deleted;



    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<VacationTemp> vacationTemps = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Duty> duties = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "position_name")
    private Position position;


    @ManyToOne
    @JoinColumn(name = "department_name")
    private Department department;

    @PrePersist
    public void preDeleted() {
        this.deleted = false;
    }



}

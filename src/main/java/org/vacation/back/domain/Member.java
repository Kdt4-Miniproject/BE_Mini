package org.vacation.back.domain;


import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;
import org.vacation.back.common.MemberStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Member extends BaseEntity {


    @Id
    private String username;

    @Column(columnDefinition = "TEXT")
    private String password;

    private String email;

    @Enumerated(EnumType.STRING) private Role role;


    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String birthdate;

    private Integer totalYears;


    private String fileName;


    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    private String name;

    private LocalDate joiningDay;

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Vacation> vacationTemps = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Duty> duties = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "position_name",insertable = false,updatable = false)
    private Position position;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "department_name",insertable = false,updatable = false)
    private Department department;



    @Column(name = "department_name")
    public String departmentName;

    @Column(name = "position_name")
    public String positionName;


    public void changePassword(String pwdData){
        this.password = pwdData;
    }
    public void assignEmNumber(String number){
        this.employeeNumber = number;
    }

}

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

@Setter
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



    @Enumerated(EnumType.STRING)
    private Role role;


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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_name")
    private Position position;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_name")
    private Department department;

//    @JoinColumn(name = "department_vacationLimit")
//    private String vacationLimit;



    public void changeStatus(MemberStatus memberStatus){
        this.memberStatus = memberStatus;
    }

    public void changePassword(String pwdData){
        this.password = pwdData;
    }
    public void changePhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
    public void changeEmail(String email){this.email = email;}

    public void changeFileName(String fileName){this.fileName = fileName;}

    public void changeName(String name) {this.name = name;}

    public void changeBirthDate(String birthdate){this.birthdate =birthdate;}

    public void changeJoiningDay(String joingDay){this.joiningDay = LocalDate.parse(joingDay);}

    public void changeYears(String years){this.totalYears = Integer.parseInt(years);}

    public void changeRole(Role role){this.role =role;}



    public void assignEmNumber(String number){
        this.employeeNumber = number;
    }

    public void changePosition(Position position){
        this.position = position;
    }
    public void changeDepartment(Department department){
        this.department = department;
    }



}

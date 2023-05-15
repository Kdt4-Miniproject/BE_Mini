package org.vacation.back.repository.impl;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.config.EnableConfig;
import org.vacation.back.domain.*;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.repository.VacationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Optional;


@Import(EnableConfig.class)
@DataJpaTest
public class VacationRespositoryImplTest {

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PositionRepository positionRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
//        departmentRepository.save(Department.builder()
//                .departmentName("개발")
//                .departmentPersonal(10)
//                .build());
//        Department department = departmentRepository.save(Department.builder()
//                .departmentName("인사")
//                .departmentPersonal(10)
//                .build());
//        departmentRepository.save(Department.builder()
//                .departmentName("마케팅")
//                .departmentPersonal(10)
//                .build());
//
//        Position position = positionRepository.save(Position.builder()
//                .positionName("대리")
//                .vacation("40")
//                .build());
//
//        memberRepository.save(Member.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("1234"))
//                .role(Role.ADMIN)
//                .department(department)
//                .position(position)
//                .name("관리자")
//                .birthdate("2022-33-12")
//                .email("test@naver.com")
//                .employeeNumber("20221234")
//                .memberStatus(MemberStatus.ACTIVATION)
//                .phoneNumber("010-1234-1234")
//                .build());
//
//        memberRepository.save(Member.builder()
//                .username("user")
//                .password(passwordEncoder.encode("1234"))
//                .birthdate("2022-33-12")
//                .department(department)
//                .position(position)
//                .name("유저")
//                .email("test@naver.com")
//                .employeeNumber("20221235")
//                .phoneNumber("010-1234-1234")
//                .memberStatus(MemberStatus.ACTIVATION)
//                .build());
//
//        Member member = memberRepository.findById("user").orElseThrow();
//        //given2
//        vacationRepository.save(Vacation.builder()
//                .id(1L)
//                .member(member)
//                .start(LocalDate.parse("2023-05-01"))
//                .end(LocalDate.parse("2023-05-01"))
//                .status(VacationStatus.WAITING)
//                .build());
    }
    @Test
    @DisplayName("vacationSave")
    void vacation_save(){
//        //given1
//        Member member = memberRepository.findById("user").get();
//        //given2
//        vacationRepository.save(Vacation.builder()
//                        .id(1L)
//                        .member(member)
//                        .start(LocalDate.parse("2023-05-01"))
//                        .end(LocalDate.parse("2023-05-01"))
//                        .status(VacationStatus.WAITING)
//                .build());
    }

    @Test
    @DisplayName("vacationDetail")
    void vacation_detail(){
//        Vacation vacation = vacationRepository.findById(1L).get();
//
//        Vacation vacation1 = vacationRepository.findByVacationId(1L);
//        System.out.println("===========================");
//        System.out.println(vacation.getMember().getName());
//        Assertions.assertThat(vacation.getMember().getName()).isEqualTo("유저");
//        Assertions.assertThat(vacation.getMember().getDepartment().getDepartmentName()).isEqualTo("인사");
    }
}

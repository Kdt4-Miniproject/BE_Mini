package org.vacation.back.service.vacation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.*;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.MemberService;
import org.vacation.back.service.VacationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

@SpringBootTest
public class VacationServiceImplTest {
    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private VacationService vacationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        departmentRepository.save(Department.builder()
                .departmentName("개발")
                .departmentPersonal(10)
                .build());
        Department department = departmentRepository.save(Department.builder()
                .departmentName("인사")
                .departmentPersonal(10)
                .build());
        departmentRepository.save(Department.builder()
                .departmentName("마케팅")
                .departmentPersonal(10)
                .build());

        Position position = positionRepository.save(Position.builder()
                .positionName("대리")
                .vacation("40")
                .build());

        memberRepository.save(Member.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .department(department)
                .position(position)
                .name("관리자")
                .birthdate("2022-33-12")
                .email("test@naver.com")
                .employeeNumber("20221234")
                .memberStatus(MemberStatus.ACTIVATION)
                .phoneNumber("010-1234-1234")
                .build());

        memberRepository.save(Member.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .birthdate("2022-33-12")
                .department(department)
                .position(position)
                .name("유저")
                .email("test@naver.com")
                .employeeNumber("20221235")
                .phoneNumber("010-1234-1234")
                .memberStatus(MemberStatus.ACTIVATION)
                .build());
        //given2
        String username = "user";

        VacationSaveRequestDTO save_test1 = VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-05-01"))
                .end(LocalDate.parse("2023-05-01"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test1);

        VacationSaveRequestDTO save_test2 = VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-05-02"))
                .end(LocalDate.parse("2023-05-02"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test2);

        VacationSaveRequestDTO save_test3 = VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-06-01"))
                .end(LocalDate.parse("2023-06-02"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test3);
    }

    @Test
    @DisplayName("service_save")
    void vacatioin_save(){
        Member member = memberRepository.findById("user").orElseThrow();

        vacationService.vacationSave(VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-05-08"))
                .end(LocalDate.parse("2023-05-08"))
                .status(VacationStatus.WAITING)
                .build());

    }

    @Test
    @DisplayName("service_detail")
    void vacation_detail(){
        Member member = memberRepository.findById("user").orElseThrow();

        vacationService.vacationSave(VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-05-08"))
                .end(LocalDate.parse("2023-05-08"))
                .status(VacationStatus.WAITING)
                .build());

        VacationResponseDTO vacation = vacationService.vacationDetail(1L);

        Assertions.assertThat(vacation.getMemberName()).isEqualTo("유저");
        Assertions.assertThat(vacation.getDepartment()).isEqualTo("인사");
    }
}

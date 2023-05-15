package org.vacation.back.service.duty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Position;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.*;
import org.vacation.back.service.DutyService;
import org.vacation.back.service.VacationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class DutyServiceImplTest {

        @Autowired
        private DutyRepository dutyRepository;

        @Autowired
        private MemberRepository memberRepository;

        @Autowired
        private DepartmentRepository departmentRepository;

        @Autowired
        private PositionRepository positionRepository;
        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        private DutyService dutyService;

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


            DutySaveRequestDTO save_test1 = DutySaveRequestDTO.builder()
                    .username("user")
                    .day(LocalDate.parse("2023-05-15"))
                    .status(DutyStatus.WAITING)
                    .build();
            dutyService.dutySave(save_test1); // 테스트 코드 1

            DutySaveRequestDTO save_test2 = DutySaveRequestDTO.builder()
                    .username("user")
                    .day(LocalDate.parse("2023-05-"))
                    .status(DutyStatus.WAITING)
                    .build();
            dutyService.dutySave(save_test2); // 테스트 코드 2

            DutySaveRequestDTO save_test3 = DutySaveRequestDTO.builder()
                    .username("user")
                    .day(LocalDate.parse("2023-06-01"))
                    .status(DutyStatus.WAITING)
                    .build();
            dutyService.dutySave(save_test3); // 테스트 코드 3

        }

    @Test
    void testDuplicateDutySave() {
        String username = "user";

        // 첫 번째 당직 신청
        DutySaveRequestDTO save_test1 = DutySaveRequestDTO.builder()
                .username(username)
                .day(LocalDate.parse("2023-05-01"))
                .status(DutyStatus.WAITING)
                .build();
        dutyService.dutySave(save_test1);

        // 중복 당직 신청
        DutySaveRequestDTO save_test2 = DutySaveRequestDTO.builder()
                .username(username)
                .day(LocalDate.parse("2023-05-01"))
                .status(DutyStatus.WAITING)
                .build();
        try {
            dutyService.dutySave(save_test2);
        } catch (CommonException e) {
            // 에러 코드 확인
            assertEquals(ErrorCode.EXIST_DUTY, e.getErrorCode());
            assertEquals("이미 당직이 존재합니다.", e.getMessage());
        }
    }
}


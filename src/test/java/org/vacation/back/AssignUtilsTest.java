package org.vacation.back;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.utils.AssignUtils;

import java.time.LocalDate;
import java.util.List;

@DisplayName("assignTest")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AssignUtilsTest {

    @Autowired
    private AssignUtils assignUtils;

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {

        dutyRepository.deleteAll();

        for (int i = 1; i <= 10; i++) {
            Member member = new Member();
            member.setUsername("Member" + i);
            member.setMemberStatus(MemberStatus.ACTIVATION);
            memberRepository.save(member);

            Duty duty = new Duty();
            duty.setDay(LocalDate.now().withDayOfMonth(i));
            duty.setMember(member);
            duty.setStatus(DutyStatus.WAITING);
            dutyRepository.save(duty);
        }
    }

    @Test
    public void testAssign() {
        // 기존 데이터의 상태 확인
        List<Duty> originalDuties = dutyRepository.findAllByStatus(DutyStatus.WAITING);
        for (Duty duty : originalDuties) {
            System.out.println("기존 데이터 - 날짜: " + duty.getDay() + " | 유저네임: " + duty.getMember().getUsername() + " | 상태: " + duty.getStatus());

            System.out.println("=============================================");
        }

        assignUtils.assign();

        // 변경된 데이터의 상태 확인
        List<Duty> updatedDuties = dutyRepository.findAllByStatus(DutyStatus.OK);
        for (Duty duty : updatedDuties) {
            System.out.println("변경된 데이터 - 날짜: " + duty.getDay() + " | 유저네임: " + duty.getMember().getUsername() + " | 상태: " + duty.getStatus());
        }
    }
}
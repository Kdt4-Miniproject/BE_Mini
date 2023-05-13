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
        // Member 객체 생성 및 저장
        Member member1 = new Member();
        member1.setUsername("Member1");
        memberRepository.save(member1);

        // Member2 객체 생성 및 저장
        Member member2 = new Member();
        member2.setUsername("Member2");
        memberRepository.save(member2);

        // Duty 객체 생성 및 저장
        Duty duty1 = new Duty();
        duty1.setDay(LocalDate.now().withDayOfMonth(1));
        duty1.setMember(member1);
        duty1.setStatus(DutyStatus.WAITING);
        dutyRepository.save(duty1);

        Duty duty2 = new Duty();
        duty2.setDay(LocalDate.now().withDayOfMonth(2));
        duty2.setMember(member2);
        duty2.setStatus(DutyStatus.UPDATE_WAITING);
        dutyRepository.save(duty2);
    }

    @Test
    public void TestAssign() {
        assignUtils.assign();

        // 테스트 결과 확인
        List<Duty> duties = dutyRepository.findAll();
        for (Duty duty : duties) {
            System.out.println("날짜: " + duty.getDay() + ", | 유저네임: " + duty.getMember().getUsername() + " | 상태: " + duty.getStatus());
        }
    }
}

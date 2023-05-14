package org.vacation.back;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.common.Search;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.*;
import org.vacation.back.domain.id.PositionAndDepartmentId;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.*;
import org.vacation.back.service.MemberService;
import org.vacation.back.service.TestService;
import org.vacation.back.utils.AssignUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
class BackApplicationTests {

	@Autowired
	TestService testService;


	@Autowired
	MemberService memberService;

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	PositionRepository positionRepository;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	DutyRepository dutyRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	VacationRepository vacationRepository;


	@Autowired
	AssignUtils assignUtils;

	@Test
	void contextLoads() {
//		CommonResponse commonResponse = new CommonResponse(ErrorCode.DUPLICATED_MEMBER_NAME);
//
//
//		try{
//			testService.test();
//		}catch (CommonException e){
//			System.out.println(e.getErrorCode().getHttpStatus().value());
//			System.out.println(e.getMessage());
//			/*
//			* 409
//				Member name is duplicatged. 테스트임
//			*
//			* */
//		}
	}


	@Test
	@Transactional
	void mmember_insert_data() {
//		Department department = departmentRepository.save(Department.builder()
//				.departmentName("인사")
//				.departmentPersonal(10)
//				.build());
//
//		Department department2 = departmentRepository.save(Department.builder()
//				.departmentName("개발")
//				.departmentPersonal(10)
//				.build());
//
//
//
//		Position position = positionRepository.save(Position.builder()
//				.positionName("대리")
//				.vacation("40")
//				.build());
//		Position position2 = positionRepository.save(Position.builder()
//				.positionName("과장")
//				.vacation("40")
//				.build());
//
//		IntStream.rangeClosed(1,5).forEach(i -> {
//			RegisterMemberDTO dto = RegisterMemberDTO.builder()
//					.username("admin"+i)
//					.password("1234")
//					.birthDate("2023-04-26")
//					.phoneNumber("010-1234-1234")
//					.positionName("과장")
//					.departmentName("개발")
//					.fileName("404.jpg")
//					.name("김독자")
//					.joiningDay("2020-01-01")
//					.years("3")
//					.email("test@naver.com")
//					.build();
//
//			memberService.join(dto);
//
//		});
	}

	@Test
	void query_page() {
		// given
//		Department department = departmentRepository.save(Department.builder()
//				.departmentName("인사")
//				.departmentPersonal(10)
//				.build());
//
//		Department department2 = departmentRepository.save(Department.builder()
//				.departmentName("개발")
//				.departmentPersonal(10)
//				.build());
//
//
//
//		Position position = positionRepository.save(Position.builder()
//				.positionName("대리")
//				.vacation("40")
//				.build());
//		Position position2 = positionRepository.save(Position.builder()
//				.positionName("과장")
//				.vacation("40")
//				.build());
//
//
//		IntStream.rangeClosed(1,5).forEach(i -> {
//					Member member = Member.builder()
//							.username("user"+i)
//							.password("1234")
//							.birthdate("2023-04-26")
//							.phoneNumber("010-1234-1234")
//							.position(position2)
//							.department(department2)
//							.fileName("404.jpg")
//							.name("김독자")
//							.joiningDay(LocalDate.parse("2020-01-01"))
//							.email("test@naver.com")
//							.build();
//
//			memberRepository.save(member);
//		});
//		IntStream.rangeClosed(6,10).forEach(i -> {
//			Member member = Member.builder()
//					.username("user"+i)
//					.password("1234")
//					.birthdate("2023-04-26")
//					.phoneNumber("010-1234-1234")
//					.position(position2)
//					.department(department)
//					.fileName("404.jpg")
//					.name("김독자")
//					.joiningDay(LocalDate.parse("2020-01-01"))
//					.email("test@naver.com")
//					.build();
//
//			memberRepository.save(member);
//		});
		// when
//		Member member = memberRepository.findById("user2").get(); //Member 개발
//		Member member2 = memberRepository.findById("user7").get();	//Member2 인사
//
//
//		Vacation vacation = Vacation.builder()
//				.status(VacationStatus.WAITING)
//				.start(LocalDate.now().minusDays(7L))
//				.end(LocalDate.now().minusDays(3L))
//				.member(member)
//				.build();
//		Vacation vacation2 = Vacation.builder()
//				.status(VacationStatus.WAITING)
//				.start(LocalDate.now().minusDays(7L))
//				.end(LocalDate.now().minusDays(3L))
//				.member(member2)
//				.build();
//
//
//
//		vacationRepository.save(vacation);
//		vacationRepository.save(vacation2);

//
//		memberRepository.save(Member.builder()
//						.username("admin")
//						.password(encoder.encode("1234"))
//						.role(Role.ADMIN)
//						.memberStatus(MemberStatus.ACTIVATION)
//						.name("나는야 어드민")
//						.employeeNumber("20220103")
//				.build());}
//		vacationRepository.save(Vacation.builder()
//						.member(memberRepository.findById(u))
//				.build());

	}

	@Test
	void mock_data_insert() {
	    // given

		//인사

	    // when

	    // then
	}


}

package org.vacation.back;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.Search;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Position;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.service.MemberService;
import org.vacation.back.service.TestService;

import java.time.LocalDate;
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
	PasswordEncoder encoder;



	@Test
	void contextLoads() {
		CommonResponse commonResponse = new CommonResponse(ErrorCode.DUPLICATED_MEMBER_NAME);


		try{
			testService.test();
		}catch (CommonException e){
			System.out.println(e.getErrorCode().getHttpStatus().value());
			System.out.println(e.getMessage());
			/*
			* 409
				Member name is duplicatged. 테스트임
			*
			* */
		}
	}


	@Test
	@Transactional
	void mmember_insert_data() {
		Department department = departmentRepository.save(Department.builder()
				.departmentName("인사")
				.departmentPersonal(10)
				.build());

		Department department2 = departmentRepository.save(Department.builder()
				.departmentName("개발")
				.departmentPersonal(10)
				.build());



		Position position = positionRepository.save(Position.builder()
				.positionName("대리")
				.vacation("40")
				.build());
		Position position2 = positionRepository.save(Position.builder()
				.positionName("과장")
				.vacation("40")
				.build());

		IntStream.rangeClosed(1,5).forEach(i -> {
			RegisterMemberDTO dto = RegisterMemberDTO.builder()
					.username("admin"+i)
					.password("1234")
					.birthDate("2023-04-26")
					.phoneNumber("010-1234-1234")
					.positionName("과장")
					.departmentName("개발")
					.fileName("404.jpg")
					.name("김독자")
					.joiningDay("2020-01-01")
					.years("3")
					.email("test@naver.com")
					.build();

			memberService.join(dto);

		});
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
		memberRepository.findAll().forEach(
				member ->
				{
					member.changePassword(encoder.encode("1234"));
					memberRepository.save(member);
				}
		);
	    // then
	}

}

package org.vacation.back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.common.DepartClassification;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.*;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.repository.VacationRepository;

import java.time.LocalDate;


@EnableJpaAuditing
@SpringBootApplication
public class BackApplication {

	@Profile("dev")
	@Bean
	CommandLineRunner initData(MemberRepository memberRepository,
							   PasswordEncoder passwordEncoder,
							   PositionRepository positionRepository,
							   DepartmentRepository departmentRepository,
							   VacationRepository vacationRepository
	){
		return (args)->{
			departmentRepository.save(Department.builder()
					.departmentName("개발")
					.departmentPersonal(10)
					.build());
			Department department = departmentRepository.save(Department.builder()
					.departmentName("인사")
					.departmentPersonal(10)
					.vacationLimit(2)
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

			Member member1 = memberRepository.save(Member.builder()
					.username("user")
					.name("김독자")
					.password(passwordEncoder.encode("1234"))
					.birthdate("2022-33-12")
					.department(department)
					.position(position)
					.role(Role.ADMIN)
					.email("test@naver.com")
					.memberStatus(MemberStatus.ACTIVATION)
					.employeeNumber("20221235")
					.phoneNumber("010-1234-1234")
					.build());

			// 아래 삭제 예정
			Member member2 = memberRepository.save(Member.builder()
					.username("test1")
					.name("김독자")
					.password(passwordEncoder.encode("1234"))
					.birthdate("2022-33-12")
					.department(department)
					.position(position)
					.role(Role.ADMIN)
					.email("test@naver.com")
					.memberStatus(MemberStatus.ACTIVATION)
					.employeeNumber("20221235")
					.phoneNumber("010-1234-1234")
					.build());
			Member member3 = memberRepository.save(Member.builder()
					.username("test2")
					.name("김독자")
					.password(passwordEncoder.encode("1234"))
					.birthdate("2022-33-12")
					.department(department)
					.position(position)
					.role(Role.ADMIN)
					.email("test@naver.com")
					.memberStatus(MemberStatus.ACTIVATION)
					.employeeNumber("20221235")
					.phoneNumber("010-1234-1234")
					.build());
			Member member4 = memberRepository.save(Member.builder()
					.username("test3")
					.name("김독자")
					.password(passwordEncoder.encode("1234"))
					.birthdate("2022-33-12")
					.department(department)
					.position(position)
					.role(Role.ADMIN)
					.email("test@naver.com")
					.memberStatus(MemberStatus.ACTIVATION)
					.employeeNumber("20221235")
					.phoneNumber("010-1234-1234")
					.build());
			vacationRepository.save(Vacation.builder()
							.id(1L)
							.start(LocalDate.parse("2023-05-01"))
					.end(LocalDate.parse("2023-05-03"))
							.status(VacationStatus.OK)
							.member(member1)
					.build());
			vacationRepository.save(Vacation.builder()
					.id(2L)
					.start(LocalDate.parse("2023-05-02"))
					.end(LocalDate.parse("2023-05-04"))
					.status(VacationStatus.OK)
					.member(member1)
					.build());
			vacationRepository.save(Vacation.builder()
					.id(3L)
					.start(LocalDate.parse("2023-05-04"))
					.end(LocalDate.parse("2023-05-05"))
					.status(VacationStatus.OK)
					.member(member1)
					.build());
			vacationRepository.save(Vacation.builder()
					.id(4L)
					.start(LocalDate.parse("2023-05-06"))
					.end(LocalDate.parse("2023-05-07"))
					.status(VacationStatus.OK)
					.member(member1)
					.build());
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}

package org.vacation.back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.common.*;

import org.vacation.back.domain.*;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.repository.VacationRepository;

import java.time.LocalDate;

import java.util.stream.IntStream;



@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class BackApplication {

	@Profile("dev")
	@Bean
	CommandLineRunner initData(MemberRepository memberRepository,
							   PasswordEncoder passwordEncoder,
							   PositionRepository positionRepository,
							   DepartmentRepository departmentRepository,
							   DutyRepository dutyrepository
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


				memberRepository.save(Member.builder()
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



		};
	}
	@Profile("prod")
	@Bean
	CommandLineRunner initDataProduct(MemberRepository memberRepository,
							   PasswordEncoder passwordEncoder,
							   PositionRepository positionRepository,
							   DepartmentRepository departmentRepository
	){
		return (args)->{
			if(memberRepository.findAll().isEmpty()){
				Department department1 = departmentRepository.save(Department.builder()
						.departmentName("관리")
						.departmentPersonal(1)
						.vacationLimit(3)
						.status(DepartmentStatus.ACTIVATION)
						.build());

				Department department2 = departmentRepository.save(Department.builder()
						.departmentName("개발")
						.departmentPersonal(10)
						.vacationLimit(3)
						.status(DepartmentStatus.ACTIVATION)
						.build());
				Department department3 = departmentRepository.save(Department.builder()
						.departmentName("인사")
						.departmentPersonal(10)
						.vacationLimit(3)
						.status(DepartmentStatus.ACTIVATION)
						.build());
				Department department4 = departmentRepository.save(Department.builder()
						.departmentName("마케팅")
						.departmentPersonal(10)
						.vacationLimit(3)
						.status(DepartmentStatus.ACTIVATION)
						.build());

				Position position2 = positionRepository.save(Position.builder()
						.positionName("사원")
						.vacation("40")
						.status(PositionStatus.ACTIVATION)
						.build());


				Position position3 = positionRepository.save(Position.builder()
						.positionName("대리")
						.vacation("40")
						.status(PositionStatus.ACTIVATION)
						.build());

				Position position4 = positionRepository.save(Position.builder()
						.positionName("팀장")
						.vacation("40")
						.status(PositionStatus.ACTIVATION)
						.build());


				Position position1 = positionRepository.save(Position.builder()
						.positionName("어드민")
						.vacation("40")
						.status(PositionStatus.ACTIVATION)
						.build());



				memberRepository.save(Member.builder()
						.username("admin")
						.password(passwordEncoder.encode("1234"))
						.role(Role.ADMIN)
						.department(department1)
						.position(position1)
						.name("관리자")
						.birthdate("2022-33-12")
						.email("test@naver.com")
						.employeeNumber("20221234")
						.memberStatus(MemberStatus.ACTIVATION)
						.phoneNumber("010-1234-1234")
						.build());

				IntStream.rangeClosed(1,10).forEach(value -> {
					memberRepository.save(Member.builder()
							.username("user"+value)
							.password(passwordEncoder.encode("1234"))
							.role(Role.STAFF)
							.department(department2)
							.position(position2)
							.name("관리자")
							.birthdate("2022-33-12")
							.email("test@naver.com")
							.employeeNumber("20221234")
							.memberStatus(MemberStatus.ACTIVATION)
							.phoneNumber("010-1234-1234")
							.build());
				});

				IntStream.rangeClosed(11,20).forEach(value -> {
					memberRepository.save(Member.builder()
							.username("user"+value)
							.password(passwordEncoder.encode("1234"))
							.role(Role.STAFF)
							.department(department3)
							.position(position4)
							.name("관리자")
							.birthdate("2022-33-12")
							.email("test@naver.com")
							.employeeNumber("20221234")
							.memberStatus(MemberStatus.ACTIVATION)
							.phoneNumber("010-1234-1234")
							.build());
				});
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}

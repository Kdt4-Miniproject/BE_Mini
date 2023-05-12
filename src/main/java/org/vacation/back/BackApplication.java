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
import org.vacation.back.domain.*;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;


@EnableJpaAuditing
@SpringBootApplication
public class BackApplication {

	@Profile("dev")
	@Bean
	CommandLineRunner initData(MemberRepository memberRepository,
							   PasswordEncoder passwordEncoder,
							   PositionRepository positionRepository,
							   DepartmentRepository departmentRepository
							){
		return (args)->{
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

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}

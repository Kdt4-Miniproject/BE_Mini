package org.vacation.back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Role;
import org.vacation.back.repository.MemberRepository;

@SpringBootApplication
public class BackApplication {

	@Profile("dev")
	@Bean
	CommandLineRunner initData(MemberRepository memberRepository, PasswordEncoder passwordEncoder){
		return (args)->{
				memberRepository.save(Member.builder()
								.username("admin")
								.password(passwordEncoder.encode("1234"))
								.role(Role.ADMIN)
								.birthdate("2022-33-12")
								.email("test@naver.com")
								.years(14)
								.employeeNumber("202212341234")
								.phoneNumber("010-1234-1234")
						.build());

				memberRepository.save(Member.builder()
						.username("user")
						.password(passwordEncoder.encode("1234"))
						.role(Role.STAFF)
						.birthdate("2022-33-12")
						.email("test@naver.com")
						.years(15)
						.employeeNumber("202212341234")
						.phoneNumber("010-1234-1234")
						.build());
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}

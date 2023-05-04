package org.vacation.back.repository.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.config.EnableConfig;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Role;
import org.vacation.back.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;


@Import(EnableConfig.class)
@DataJpaTest
class MemberRepositoryImplTest {


    @Autowired
    private MemberRepository memberRepository;

    PasswordEncoder encoder = new BCryptPasswordEncoder();
    @BeforeEach
    void setUp(){
        memberRepository.save(Member.builder()
                .username("admin")
                .password(encoder.encode("1234"))
                .role(Role.ADMIN)
                .birthdate("2022-33-12")
                .email("test@naver.com")
                .years(14)
                .employeeNumber("202212341234")
                .phoneNumber("010-1234-1234")
                .build());
    }

    /**
     * @apiNote 존재하면 true/ 없는 데이터면 false
     * */
    @Test
    @DisplayName("해당 username이 존재하는지 테스트한다.")
    void member_username_checking() {
        // given

        String success = "admin";
        String failure = "user1";
        // when

        boolean checking1 = memberRepository.exist(success);
        boolean checking2 = memberRepository.exist(failure);
        // then

        Assertions.assertThat(checking1).isTrue(); //해당 아이디가 존재할 경우
        Assertions.assertThat(checking2).isFalse(); //해당 아이디가 존재하지 않을 경우

    }


    /**
     * deleted는 자동 where , insert 가능
     * memberStatus는 수정사항도 많기 때문에 저장할 때 지정이 필요하고 수정도 자주 이루어져야 한다.
     * */
    @Test
    @DisplayName("where 테스트")
    void member_deleted_auto() {
        // given
        memberRepository.save(Member.builder()
                .username("admin")
                .password(encoder.encode("1234"))
                .role(Role.ADMIN)
                .birthdate("2022-33-12")
                .memberStatus(MemberStatus.WAITING)
                .email("test@naver.com")
                .years(14)
                .employeeNumber("202212341234")
                .phoneNumber("010-1234-1234")
                .build());
        // when

         Member member =  memberRepository.findById("admin").get();
        // then
        Assertions.assertThat(member.getUsername()).isEqualTo("admin");
        Assertions.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.WAITING);
    }

}
package org.vacation.back.service.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.vacation.back.common.Search;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Position;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.request.member.AdminMemberModifyRequest;
import org.vacation.back.dto.request.member.MemberModifyDTO;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.dto.request.member.RoleChangeRequest;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.service.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberServiceImplTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;



    @Test
    @DisplayName("")
    void mebmer_join_test() {
        // given


        RegisterMemberDTO dto = RegisterMemberDTO.builder()
                .username("admin2")
                .password("1234")
                .birthDate("2023-04-26")
                .phoneNumber("010-1234-1234")
                .positionName("대리")
                .departmentName("개발")
                .fileName("404.jpg")
                .name("김독자")
                .joiningDay("2020-01-01")
                .years("3")
                .email("test@naver.com")
                .build();

        // when
        memberService.join(dto);
        // then

        Member member = memberRepository.findById("admin2").get();

        Assertions.assertThat(member).isNotNull();
    }


    @Test
    @DisplayName("")
    void member_querydsl_test() {
        // given
       Page<Member> page = memberRepository.pageMember(Search.ALL,"", PageRequest.of(0,5));

        // when
        System.out.println(page.getContent().size());
        System.out.println(page.getTotalElements());

        // then
    }

    @Test
    @DisplayName("")
    void departement_and_position() {
        // given
        List<Position> list = entityManager
                .createQuery("SELECT pad.positionName FROM PositionAndDepartment pad WHERE pad.departmentName.departmentName = :departmentName", Position.class)
                .setParameter("departmentName","인사")
                .getResultList();
        // when
        list.forEach(position -> {
            System.out.println(position.getPositionName());
        });
        // then
    }

    @Test
    @DisplayName("")
    void member_modify_test() {
        // given
        MemberModifyDTO memberModifyDTO = MemberModifyDTO.builder()
                .phoneNumber("010-9871-1234")
                .oldPassword("1234")
                .newPassword("22334")
                .fileName("find.png")
                .email("text@nate.com")
                .build();
        // when
        boolean result = memberService.memberModify(memberModifyDTO,"admin2");

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("")
    void member_admin_modify_test() {
        // given
        AdminMemberModifyRequest request = AdminMemberModifyRequest.builder()

                .username("admin2")
                .name("나는야")
                .name("email@test.com")
                .phoneNumber("010-2222-2222")
                .birthDate("1111-11-11")
                .joiningDay("2022-01-01")
                .fileName("101.jpg")
                .build();
        // when
        boolean result =memberService.adminModify(request);
        // then
        Assertions.assertThat(result).isTrue();
    }


    @Test
    @DisplayName("")
    void member_admin_role_change() {
        // given
        RoleChangeRequest request = RoleChangeRequest.builder()
                .username("admin2")
                .role(Role.ADMIN)
                .build();

        // when
        memberService.adminRoleModify(request);
        // then
    }
}
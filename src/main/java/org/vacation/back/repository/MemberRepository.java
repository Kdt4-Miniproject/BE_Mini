package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;
import org.vacation.back.repository.child.CustomMemberRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String>, CustomMemberRepository {


    @Query("SELECT v FROM Vacation v  join fetch v.member WHERE v.member.username in (:usernams)")
    public Optional<List<Vacation>> vacationByUsername(@Param("usernams") List<String> usernames);

    @Query("SELECT m FROM Member m WHERE m.memberStatus = :status")
    List<Member> findAllActivation(@Param("status") MemberStatus status);


}

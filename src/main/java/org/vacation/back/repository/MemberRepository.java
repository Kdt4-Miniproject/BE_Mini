package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;
import org.vacation.back.repository.child.CustomMemberRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String>, CustomMemberRepository {



}

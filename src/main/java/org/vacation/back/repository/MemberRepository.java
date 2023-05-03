package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vacation.back.domain.Member;
import org.vacation.back.repository.child.CustomMemberRepository;

public interface MemberRepository extends JpaRepository<Member,String>, CustomMemberRepository {
}

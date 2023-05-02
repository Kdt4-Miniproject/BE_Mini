package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vacation.back.domain.Member;

public interface MemberRepository extends JpaRepository<Member,String> {
}

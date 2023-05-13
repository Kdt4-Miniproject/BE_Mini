package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DutyRepository  extends JpaRepository<Duty, Long> {


    @Query("select d from Duty d where d.member.username = :username and d.day = :day")
    Duty findByDutyAndDay(@Param("username") String username, @Param("day") LocalDate day);

    @Query("select d from Duty d join fetch d.member m where d.status <> 'DELETED'")
    List<Duty> findAllByDutyStatus();

    @Query("select d from Duty d join fetch d.member m where month(d.day) = :month AND d.status <> 'DELETED'")
    List<Duty> findAllByDutyMonth(@Param("month") Integer month);

    @Query("select d from Duty d join fetch d.member m where d.id = :id")
    Duty findByDutyId(@Param("id") Long id);

    @Query("select d from Duty d where d.day = :day")
    Duty findByDay(@Param("day") LocalDate day);


    @Query("Select d from Duty d where d.member.username = :username and d.day = :day")
    Duty findByDutyDay(@Param("username") String username, @Param("day") LocalDate day);

    @Query("select d from Duty d join fetch d.member m where d.id = :id")
    Duty findByDuty(@Param("id") Long id);

    @Query("select m from Member m join fetch m.department where m.username = :username and m.memberStatus = :status")
    Member findByMember(@Param("username") String username, @Param("status")MemberStatus status);

    @Query("SELECT d FROM Duty d WHERE d.status = 'Waiting' OR d.status = 'OK'")
    List<Duty> findAllDutyStatus();

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.duties")
    List<Member> findByAllduties();

}

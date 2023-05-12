package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("select v from Vacation v where v.member.username = :username and v.start = :start")
    Vacation findByVacationStart(@Param("username") String username, @Param("start") LocalDate start);

    //해당 달만 정보를 넘겨줘야함

    @Query("select v from Vacation v join fetch v.member m WHERE MONTH(v.start) = :month AND v.status <> 'DELETED'")
    List<Vacation> findAllByVacationMonth(@Param("month") Integer month);

    //inner join
    @Query("select v from Vacation v join fetch v.member m WHERE v.status = :status AND v.status <> 'DELETED'")
    List<Vacation> findAllByVacationStatus(@Param("status") VacationStatus status);

    @Query("select v from Vacation v join fetch v.member m where v.id = :id")
    Vacation findByVacationId(@Param("id") Long id);

    @Query("select v from Vacation v join fetch v.member m where v.id = :id")
    Vacation findByvacation(@Param("id") Long id);

    @Query("select m from Member m join fetch m.department where m.username = :username and m.memberStatus = :status")
    Member findBymember(@Param("username") String username, @Param("status")MemberStatus status);
}

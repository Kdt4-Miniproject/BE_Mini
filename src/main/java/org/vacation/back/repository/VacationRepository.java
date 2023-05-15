package org.vacation.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long>{



    @Query("select v from Vacation v where v.member.username = :username and v.start = :start")
    Vacation findByVacationStart(@Param("username") String username, @Param("start") LocalDate start);

    //해당 달만 정보를 넘겨줘야함

    @Query(value = "select v from Vacation v join fetch v.member m WHERE FUNCTION('MONTH', v.start) = :month AND v.status <> 'DELETED'",
            countQuery = "select count(v) from Vacation v join v.member m WHERE FUNCTION('MONTH', v.start) = :month AND v.status <> 'DELETED'")
    Page<Vacation> findAllByVacationMonth(@Param("month") Integer month, Pageable pageable);


    //inner join
    @Query(value = "select v from Vacation v join fetch v.member m WHERE v.status = 'WAITING' or v.status = 'UPDATE_WAITING'")
    List<Vacation> findAllByVacationStatus();

    @Query("select v from Vacation v where v.member.username = :userName AND v.status <> 'DELETED'")
    List<Vacation> findByVacationUserName(@Param("userName") String userName);

    @Query("select v from Vacation v join fetch v.member m where v.id = :id")
    Optional<Vacation> findByvacation(@Param("id") Long id);

    @Query("select v from Vacation v join fetch v.member where v.member.department.departmentName = :departmentName and v.status = 'OK'")
    List<Vacation> findAllByDepartment(@Param("departmentName") String departmentName);

    @Query("select m from Member m join fetch m.department join fetch m.position where m.username = :username and m.memberStatus = :status")
    Member findBymember(@Param("username") String username, @Param("status")MemberStatus status);
}

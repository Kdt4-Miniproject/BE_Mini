package org.vacation.back.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DutyRepository  extends JpaRepository<Duty, Long> {

    @Query("select d from Duty d where MONTH(d.day) = :month")
    public List<Duty> findAll(@Param("month") Integer month);
    @Query("select d from Duty d where d.member.username = :username and d.day = :day")
    Duty findByDutyAndDay(@Param("username") String username, @Param("day") LocalDate day);

    @Query(value = "select d from Duty d join fetch d.member m where d.status = 'WAITING' or d.status = 'UPDATE_WAITING' ",
            countQuery = "select count(d) from Duty d join d.member m WHERE FUNCTION('MONTH', d.day) = :month")
    Page<Duty> findAllByDutyStatus(Pageable pageable);

    @Query(value = "select d from Duty d join fetch d.member m where month(d.day) = :month AND d.status <> 'DELETED'",
            countQuery = "select count(d) from Duty d join d.member m WHERE FUNCTION('MONTH', d.day) = :month AND d.status <> 'DELETED'")
    Page<Duty> findAllByDutyMonth(@Param("month") Integer month, Pageable pageable);

    @Query("select d from Duty d join fetch d.member m where d.id = :id")
    Duty findByDutyId(@Param("id") Long id);

    @Query("select d from Duty d where d.day = :day")
    Duty findByDay(@Param("day") LocalDate day);


    @Query("Select d from Duty d where d.day = :day")
    Duty findByDutyDay(@Param("day") LocalDate day);

    @Query("select d from Duty d join fetch d.member m where d.id = :id")
    Optional<Duty> findByDuty(@Param("id") Long id);

    @Query("select m from Member m join fetch m.department where m.username = :username and m.memberStatus = :status")
    Member findByMember(@Param("username") String username, @Param("status")MemberStatus status);

    @Query("SELECT d FROM Duty d WHERE d.status = 'Waiting' OR d.status = 'OK'")
    List<Duty> findAllDutyStatus();

    @Query("SELECT DISTINCT m FROM Member m JOIN FETCH m.duties")
    List<Member> findByAllduties();

    @Query("SELECT d FROM Duty d WHERE d.status = :status AND MONTH(d.day) = :month")
    List<Duty> findDutiesByStatusAndMonth(@Param("status") DutyStatus status, @Param("month") int month);

    @Query("SELECT d FROM Duty d WHERE d.status = :status")
    List<Duty> findAllByStatus(@Param("status") DutyStatus status);

    @Query("select d from Duty d join fetch d.member where d.member.department.departmentName = :departmentName and d.status = 'OK'")
    List<Duty> findAllByDepartment(@Param("departmentName") String departmentName);

    @Query("select d from Duty d where d.day = :day AND d.status = :status")
    Duty findByDayAndOk(@Param("day") LocalDate day,@Param("status") DutyStatus status);
    @Query("select d from Duty d where d.day = :day AND d.status = :status")
    List<Duty> findByDayAndStatus(@Param("day") LocalDate day,@Param("status") DutyStatus status);

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.memberStatus = :memberStatus")
    Member findByUsernameAndMemberStatus(@Param("username") String username, @Param("memberStatus") MemberStatus memberStatus);

}

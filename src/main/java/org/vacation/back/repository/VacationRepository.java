package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;
import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("select v from Vacation v where v.member.username = :username and v.start = :start")
    Vacation findByVacationStart(@Param("username") String username, @Param("start") LocalDate start);

    //해당 달만 정보를 넘겨줘야함

    @Query("SELECT v FROM Vacation v WHERE MONTH(v.start) = :month")
    List<Vacation> findAllByVacationMonth(@Param("month") Integer month);

    @Query("SELECT v FROM Vacation v WHERE v.status = :status")
    List<Vacation> findAllByVacationStatus(@Param("status") VacationStatus status);

    @Query("select v, m, d.departmentName from Vacation v left join v.member m left join m.department d where v.id = :id")
    Vacation findByVacationId(@Param("id") Long id);
}

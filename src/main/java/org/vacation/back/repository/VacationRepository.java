package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    @Query("select vt from Vacation vt where vt.member.username = :username and vt.start = :start")
    public Vacation findByVacationStart(@Param("username") String username, @Param("start") LocalDate start);
}

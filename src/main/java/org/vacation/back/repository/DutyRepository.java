package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.domain.Duty;

import java.util.List;

public interface DutyRepository  extends JpaRepository<Duty,Long> {

    @Query("select d from Duty d where MONTH(d.day) = :month")
    public List<Duty> findAll(@Param("month") Integer month);
}

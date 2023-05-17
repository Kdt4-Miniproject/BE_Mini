package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Position;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, String> {

    @Query("select p from Position p where p.status = 'ACTIVATION'")
    List<Position> findByPositionAll();

    @Query("select p from Position p where p.status = 'ACTIVATION' and p.positionName = :positionName")
    Position findByPositionId(@Param("positionName") String positionName);

}

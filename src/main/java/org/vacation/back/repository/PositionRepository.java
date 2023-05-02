package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vacation.back.domain.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}

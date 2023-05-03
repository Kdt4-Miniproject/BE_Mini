package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vacation.back.domain.Vacation;
@Repository
public interface VacationTempRepository extends JpaRepository<Vacation, Long> {

}

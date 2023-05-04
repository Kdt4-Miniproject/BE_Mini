package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vacation.back.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department,String> {
}

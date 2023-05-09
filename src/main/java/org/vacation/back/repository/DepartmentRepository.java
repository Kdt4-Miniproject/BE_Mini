package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Position;

public interface DepartmentRepository extends JpaRepository<Department,String> {


}

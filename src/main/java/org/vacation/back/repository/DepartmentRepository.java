package org.vacation.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Position;

import java.time.LocalDate;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,String> {

    @Query("select d from Department d where d.status = 'ACTIVATION'")
    List<Department> findByDepartmentAll();

    @Query("select d from Department d where d.status = 'ACTIVATION' and d.departmentName = :departmentName")
    Department findByDepartmentId(@Param("departmentName") String departmentName);
}

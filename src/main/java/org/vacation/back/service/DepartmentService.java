package org.vacation.back.service;

import org.vacation.back.dto.common.DepartmentDTO;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;

import java.util.List;

public interface DepartmentService {

    boolean departmentSave(DepartmentSaveDTO dto);

    DepartmentDTO departmentDetail(String id);

    List<DepartmentDTO> departmentList();

    DepartmentDTO departmentModify(String id, DepartmentModifyDTO dto);

    boolean departmentDelete(String id);

}

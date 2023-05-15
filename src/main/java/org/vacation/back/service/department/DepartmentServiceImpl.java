package org.vacation.back.service.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.DepartmentStatus;
import org.vacation.back.domain.Department;
import org.vacation.back.dto.common.DepartmentDTO;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.DepartmentDuplicateException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.exception.NotFoundDepartmentException;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public boolean departmentSave(DepartmentSaveDTO dto) {
        Optional<Department> departmentOP = departmentRepository.findById(dto.getDepartmentName());
        if (departmentOP.isPresent()) {
            throw new DepartmentDuplicateException("이미 존재하는 부서입니다");
        } else {
            try {
                departmentRepository.save(dto.toEntity());
                return true;
            } catch (Exception e) {
                throw new CommonException(ErrorCode.DTO_IS_NULL);
            }
        }
    }

    public DepartmentDTO departmentDetail(String id) {
        Department dtoPS = departmentRepository.findById(id).orElseThrow(NotFoundDepartmentException::new);
        return new DepartmentDTO(dtoPS);
    }

    public List<DepartmentDTO> departmentList() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (Department department : departmentList) {
            departmentDTOList.add(new DepartmentDTO(department));
        }
        return departmentDTOList;
    }

    @Transactional
    public DepartmentDTO departmentModify(String id, DepartmentModifyDTO dto) {
        Department departmentPS = departmentRepository.findById(id).orElseThrow(NotFoundDepartmentException::new);
        departmentPS.modify(Integer.parseInt(dto.getVacationLimit()));
        return new DepartmentDTO(departmentPS);
    }

    @Transactional
    public boolean departmentDelete(String id) {
        Department departmentPS = departmentRepository.findById(id).orElseThrow(NotFoundDepartmentException::new);
        departmentPS.setStatus(DepartmentStatus.DEACTIVATION);
        return true;
    }
}

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
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

//@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;

    @Transactional
    public boolean departmentSave(DepartmentSaveDTO dto) {
        try {
            departmentRepository.save(dto.toEntity());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "입력값을 확인해 주세요");
        }
        return true;
    }

    public DepartmentDTO departmentDetail(String id) {
        Department dtoPS = departmentRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "없는 직급입니다.(조회)"));
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
        Department departmentPS = departmentRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "없는 직급입니다.(수정)"));
        departmentPS.modify(dto.getVacationLimit(), dto.getDepartmentPersonal());
        return new DepartmentDTO(departmentPS);
    }

    @Transactional
    public boolean departmentDelete(String id) {
        Department departmentPS = departmentRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "없는 직급입니다.(삭제)"));
        departmentPS.setStatus(DepartmentStatus.DEACTIVATION);
        return true;
    }
}

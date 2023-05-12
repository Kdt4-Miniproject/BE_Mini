package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.DepartmentDTO;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;
import org.vacation.back.service.DepartmentService;

import javax.validation.Valid;
import java.util.List;

;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    // TODO: 부서 추가 (관리자) - 새로운 부서 추가
    @Permission
    @PostMapping("/api/v1/department/save")  // 관리자 페이지
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid DepartmentSaveDTO dto) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(departmentService.departmentSave(dto))
                .build());
    }

    // TODO: 부서 조회
    @GetMapping("/api/v1/department/detail/{id}") // 유저 페이지
    public ResponseEntity<CommonResponse> detail(@PathVariable("id") String id) {

        DepartmentDTO dto = departmentService.departmentDetail(id);

//        DepartmentDTO dto = DepartmentDTO.builder()
//                .departmentName("개발")
//                .vacationLimit(4)
//                .departmentPersonal(6)
//                .status(DepartmentStatus.ACTIVATION)
//                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }


    // TODO: 부서 조회 (관리자) - 부서 테이블 모두보기
    @Permission
    @GetMapping("/api/v1/department/list") // 관리자 페이지
    public ResponseEntity<CommonResponse> find_all() {

        List<DepartmentDTO> dtoList = departmentService.departmentList();

//        List<DepartmentDTO> dtoList = new ArrayList<>();
//        dtoList.add(DepartmentDTO.builder()
//                .departmentName("개발")
//                .vacationLimit(4)
//                .departmentPersonal(6)
//                .status(DepartmentStatus.ACTIVATION)
//                .build());
//        dtoList.add(DepartmentDTO.builder()
//                .departmentName("영업")
//                .vacationLimit(3)
//                .departmentPersonal(7)
//                .status(DepartmentStatus.ACTIVATION)
//                .build());

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // TODO: 부서 수정 (관리자) - 명칭, 부서별 휴가제한 수, 부서 총원
    @Permission
    @PostMapping("/api/v1/department/modify/{id}") // 관리자 페이지
    public ResponseEntity<CommonResponse> modify(@PathVariable("id") String id, @RequestBody @Valid DepartmentModifyDTO dto) {

        DepartmentDTO department = departmentService.departmentModify(id, dto);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(department)
                .build());
    }

    // TODO: 부서 삭제 (관리자) - 안쓰는 부서 비활성화
    @Permission
    @PostMapping("/api/v1/department/delete/{id}") // 관리자 페이지
    public ResponseEntity<CommonResponse> delete(@PathVariable("id") String id) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(departmentService.departmentDelete(id))
                .build());
    }

}
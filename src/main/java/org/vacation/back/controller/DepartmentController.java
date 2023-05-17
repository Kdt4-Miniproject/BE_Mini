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
import org.vacation.back.exception.*;
import org.vacation.back.service.DepartmentService;

import java.util.List;
import java.util.regex.Pattern;


@Slf4j
@RequiredArgsConstructor
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 추가
    @Permission
    @PostMapping("/api/v1/department/save")
    public ResponseEntity<CommonResponse<?>> save(@RequestBody DepartmentSaveDTO dto) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", dto.getDepartmentName());

        boolean vacationLimitCheck = Pattern.matches("^[0-9]{1,2}$", dto.getVacationLimit());

        if (!idCheck)
            throw new DepartmentNameCheckException("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        if (!vacationLimitCheck)
            throw new DepartmentVacationLimitCheckException("부서별 휴가 인원 제한은 1~2자리 숫자만 입력해 주세요.");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(departmentService.departmentSave(dto))
                .build());
    }

    // 부서 조회
    @Permission
    @GetMapping("/api/v1/department/detail/{id}")
    public ResponseEntity<CommonResponse<?>> detail(@PathVariable(value = "id") String id) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", id);
        if (!idCheck)
            throw new DepartmentNameCheckException("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        DepartmentDTO dto = departmentService.departmentDetail(id);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }


    // 부서 전체 조회
    @Permission
    @GetMapping("/api/v1/department/list")
    public ResponseEntity<CommonResponse<?>> find_all() {

        List<DepartmentDTO> dtoList = departmentService.departmentList();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // 부서 수정
    @Permission
    @PostMapping("/api/v1/department/modify/{id}")
    public ResponseEntity<CommonResponse<?>> modify(@PathVariable("id") String id, @RequestBody DepartmentModifyDTO dto) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", id);
        boolean vacationLimitCheck = Pattern.matches("^[0-9]{1,2}$", dto.getVacationLimit());

        if (!idCheck)
            throw new DepartmentNameCheckException("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        if (!vacationLimitCheck)
            throw new DepartmentVacationLimitCheckException("부서별 휴가 인원 제한은 1~2자리 숫자만 입력해 주세요.");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(departmentService.departmentModify(id, dto))
                .build());
    }

    // 부서 삭제
    @Permission
    @PostMapping("/api/v1/department/delete/{id}")
    public ResponseEntity<CommonResponse<?>> delete(@PathVariable("id") String id) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", id);

        if (!idCheck)
            throw new DepartmentNameCheckException("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(departmentService.departmentDelete(id))
                .build());
    }

    @ExceptionHandler(DepartmentNameCheckException.class)
    public ResponseEntity<CommonResponse<?>> departmentNameCheckException(DepartmentNameCheckException e) {
        System.out.println("부서명 정규식표현 체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.CHECK_DEPARTMENT_NAME)
                        .data(false));
    }

    @ExceptionHandler(DepartmentVacationLimitCheckException.class)
    public ResponseEntity<CommonResponse<?>> departmentVacationLimitCheckException(DepartmentVacationLimitCheckException e) {
        System.out.println("부서 휴가인원 제한 정규식표현 체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.CHECK_DEPARTMENT_VACATION_LIMIT)
                        .data(false));
    }

    @ExceptionHandler(DepartmentDuplicateException.class)
    public ResponseEntity<CommonResponse<?>> duplicateDepartment(DepartmentDuplicateException e) {
        System.out.println("부서명 중복체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.EXIST_DEPARTMENT)
                        .data(false));
    }

    @ExceptionHandler(NotFoundDepartmentException.class)
    public ResponseEntity<CommonResponse<?>> departmentNameException(NotFoundDepartmentException e) {
        System.out.println("부서명 조회체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.NOT_FOUND_DEPARTMENT_NAME)
                        .data(false));
    }

}
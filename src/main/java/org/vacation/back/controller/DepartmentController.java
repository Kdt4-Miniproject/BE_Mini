package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vacation.back.common.DepartmentStatus;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.DepartmentDTO;
import org.vacation.back.dto.common.PositionDTO;
import org.vacation.back.dto.request.Position.PositionDeleteDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;
import org.vacation.back.dto.request.department.DepartmentDeleteDTO;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DepartmentController {

    // TODO: 부서 추가 (관리자) - 새로운 부서 추가
    @PostMapping("/api/v1/department/save")  // 관리자 페이지
    public ResponseEntity<CommonResponse> save(
            @RequestBody DepartmentSaveDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    // TODO: 부서 조회 (멤버) - 본인 부서 확인
    @GetMapping("/api/v1/department/detail") // 유저 페이지
    public ResponseEntity<CommonResponse> detail() {

        DepartmentDTO dto = DepartmentDTO.builder()
                .departmentName("MARKETING")
                .vacationLimit("4")
                .departmentPersonal("6")
                .status(DepartmentStatus.ACTIVATION)
                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }


    // TODO: 부서 조회 (관리자) - 부서 테이블 모두보기
    @GetMapping("/api/v1/department/list") // 관리자 페이지
    public ResponseEntity<CommonResponse> find_all(HttpServletRequest request) {
        List<DepartmentDTO> dtoList = new ArrayList<>();
        dtoList.add(DepartmentDTO.builder()
                .departmentName("MARKETING")
                .vacationLimit("4")
                .departmentPersonal("6")
                .status(DepartmentStatus.ACTIVATION)
                .build());
        dtoList.add(DepartmentDTO.builder()
                .departmentName("DEVELOPMENT")
                .vacationLimit("3")
                .departmentPersonal("7")
                .status(DepartmentStatus.ACTIVATION)
                .build());

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // TODO: 부서 수정 (관리자) - 명칭, 부서별 휴가제한 수, 부서 총원
    @PostMapping("/api/v1/department/modify") // 관리자 페이지
    public ResponseEntity<CommonResponse> modify(
            @RequestBody DepartmentModifyDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    // TODO: 부서 삭제 (관리자) - 안쓰는 부서 비활성화
    @PostMapping("/api/v1/department/delete") // 관리자 페이지
    public ResponseEntity<CommonResponse> delete(
            @RequestBody DepartmentDeleteDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }






































}

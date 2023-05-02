package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.VacationTemp;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.VacationTempDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationOkAndRejectedDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VacationController {

    @PostMapping("/api/v1/vacation/save")
    public ResponseEntity<CommonResponse> save(@RequestBody VacationSaveRequestDTO dto){
        //TODO: 연차 저장 로직 구현
        //TODO: 연차 신청 실패시 Exception 처리
        //TODO: vacation status WAITING 변경
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @GetMapping("/api/v1/vacation/detail/{id}")
    public ResponseEntity<CommonResponse> detail(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        //TODO: 조회하는 유저가 정보 확인
        VacationTempDTO dto = VacationTempDTO.builder()
                .id(1L)
                .username("admin")
                .start("2023-05-01")
                .end("2023-05-01")
                .deleted(false)
                .status(VacationStatus.WAITING)
                .build();
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build());
    }

    @GetMapping("/api/v1/vacation/list")
    public ResponseEntity<CommonResponse> vacationList(
            HttpServletRequest request){
        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<VacationTempDTO> vacationTempDTOList = new ArrayList<>();
        vacationTempDTOList.add(VacationTempDTO.builder()
                .id(1L)
                .username("admin")
                .start("2023-05-01")
                .end("2023-05-01")
                .deleted(false)
                .status(VacationStatus.WAITING)
                .build());
        vacationTempDTOList.add(VacationTempDTO.builder()
                .id(2L)
                .username("admin")
                .start("2023-05-01")
                .end("2023-05-01")
                .deleted(false)
                .status(VacationStatus.WAITING)
                .build());
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationTempDTOList)
                .build());
    }

    @PostMapping("/api/v1/vacation/modify/{id}")
    public ResponseEntity<CommonResponse> modify(
            @PathVariable(value = "id") Long id,
            @RequestBody VacationModifyDTO dto, Principal principal){
        //TODO: 연차 시작일 or 끝나는일 수정 가능(추후 변경가능)

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }


    @GetMapping("/api/v1/vacation/delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        //TODO: 연차신청 취소시에 사용(관리자 X)
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/api/v1/vacation/ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){
        //TODO: 연차 승인시 사용
        //TODO:
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/api/v1/vacation/rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){
        //TODO: 연차 반려시 사용
        //TODO:
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
}

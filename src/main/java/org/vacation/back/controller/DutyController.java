package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.DutyDTO;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DutyController {

    @PostMapping("/duty/save")
    public ResponseEntity<CommonResponse> save(@RequestBody DutySaveRequestDTO dutySaveRequestDTO){
        //TODO: 당직 저장 로직 구현
        //TODO: 당직 신청 실패시 Exception 처리
        //TODO: Duty status WAITING 변경
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @GetMapping("/duty/detail/{id}")
    public ResponseEntity<CommonResponse> detail(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        //TODO: 조회하는 유저가 정보 확인
        DutyDTO dutyDTO = DutyDTO.builder()
                .id(1L)
                .day("2023-05-02")
                .deleted(false)
                .status(DutyStatus.WAITING)
                .build();
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dutyDTO)
                .build());
    }

    @GetMapping("/duty/list")
    public ResponseEntity<CommonResponse> dutyList(
            HttpServletRequest request){
        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<DutyDTO> dutyDTOList = new ArrayList<>();
        dutyDTOList.add(DutyDTO.builder()
                .id(1L)
                .day("2023-05-02")
                .deleted(false)
                .status(DutyStatus.WAITING)
                .build());
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dutyDTOList)
                .build());
    }

    @PostMapping("/duty/modify/{id}")
    public ResponseEntity<CommonResponse> modify(
            @PathVariable(value = "id") Long id,
            @RequestBody DutyModifyDTO dto){
        //TODO: 당직일 수정 가능

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }


    @PostMapping("/duty/delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        //TODO: 당직신청 취소시에 사용(신청자)
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/duty/ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){
        //TODO: 당직 승인시 사용
        //TODO:
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/duty/rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){
        //TODO: 당직 반려시 사용
        //TODO:
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
}

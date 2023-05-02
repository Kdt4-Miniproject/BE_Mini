package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.PositionDTO;
import org.vacation.back.dto.request.Position.PositionDeleteDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PositionController {

    // TODO: 직급 생성 (관리자)
    @PostMapping("/api/v1/position/save") // 회원가입
    public ResponseEntity<CommonResponse> save(
            @RequestBody PositionSaveDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    // TODO: 직급 조회 (관리자, 일반유저)
    @GetMapping("/api/v1/position/detail") // 유저 페이지, 관리자 페이지 - detail
    public ResponseEntity<CommonResponse> detail() {

        PositionDTO dto = PositionDTO.builder()
                .id(1L)
                .position(PositionStatus.ASSISTANT_MANAGER)
                .vacation("1")
                .years("1")
                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }

    // TODO: 직급 전부 조회 (관리자)
    @GetMapping("/api/v1/position/list") // 관리자 페이지
    public ResponseEntity<CommonResponse> findAll(HttpServletRequest request){
        List<PositionDTO> dtoList = new ArrayList<>();
        dtoList.add(PositionDTO.builder()
                .id(1L)
                .position(PositionStatus.STAFF)
                .vacation("1")
                .years("1")
                .build());
        dtoList.add(PositionDTO.builder()
                .id(2L)
                .position(PositionStatus.MANAGER)
                .vacation("3")
                .years("2")
                .build());

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // TODO: 직급 수정 (관리자)
    @PostMapping("/api/v1/position/modify") // 관리자 페이지
    public ResponseEntity<CommonResponse> modify(
            @RequestBody PositionModifyDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    // TODO: 직급 삭제 (관리자)
    @PostMapping("/api/v1/position/delete") // 관리자 페이지 -
    public ResponseEntity<CommonResponse> delete(
            @RequestBody PositionDeleteDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }


}

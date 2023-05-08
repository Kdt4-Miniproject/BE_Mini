package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    // TODO: 직급 추가 (관리자) - 새로운 직급 추가
    @PostMapping("/api/v1/position/save")  // 관리자 페이지
    public ResponseEntity<CommonResponse> save(
            @RequestBody PositionSaveDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    // TODO: 직급 조회 (멤버) - 본인 직급 확인
    @GetMapping("/api/v1/position/detail") // 유저 페이지 - detail
    public ResponseEntity<CommonResponse> detail() {

        PositionDTO dto = PositionDTO.builder()
                .positionName("ASSISTANT_MANAGER")
                .vacation("1")
                .status(PositionStatus.ACTIVATION)
                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }

    // TODO: 직급 조회 (관리자) - 직급 테이블 모두보기
    @GetMapping("/api/v1/position/list") // 관리자 페이지
    public ResponseEntity<CommonResponse> find_all(HttpServletRequest request) {
        List<PositionDTO> dtoList = new ArrayList<>();
        dtoList.add(PositionDTO.builder()
                .positionName("STAFF")
                .vacation("1")
                .status(PositionStatus.ACTIVATION)
                .build());
        dtoList.add(PositionDTO.builder()
                .positionName("MANAGER")
                .vacation("3")
                .status(PositionStatus.ACTIVATION)
                .build());

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // TODO: 직급 수정 (관리자) - 명칭, 직급별 휴가 수
    @PostMapping("/api/v1/position/modify") // 관리자 페이지
    public ResponseEntity<CommonResponse> modify(
            @RequestBody PositionModifyDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    // TODO: 직급 삭제 (관리자) - 안쓰는 직급 비활성화
    @PostMapping("/api/v1/position/delete") // 관리자 페이지
    public ResponseEntity<CommonResponse> delete(
            @RequestBody PositionDeleteDTO dto,
            HttpServletRequest request) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }


}

package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.DepartmentDTO;
import org.vacation.back.dto.common.PositionDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;
import org.vacation.back.service.PositionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PositionController {

    private final PositionService positionService;

    // TODO: 직급 추가 (관리자) - 새로운 직급 추가
    @Permission
    @PostMapping("/api/v1/position/save")  // 관리자 페이지
    public ResponseEntity<CommonResponse> save(@RequestBody @Valid PositionSaveDTO dto) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(positionService.positionSave(dto))
                .build());
    }

    // TODO: 직급 조회 (멤버) - 본인 직급 확인
    @GetMapping("/api/v1/position/detail/{id}") // 유저 페이지 - detail
    public ResponseEntity<CommonResponse> detail(@PathVariable("id") String id) {

        PositionDTO dto = positionService.positionDetail(id);

//        PositionDTO dto = PositionDTO.builder()
//                .positionName("사원")
//                .vacation("1")
//                .status(PositionStatus.ACTIVATION)
//                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }

    // TODO: 직급 조회 (관리자) - 직급 테이블 모두보기
    @Permission
    @GetMapping("/api/v1/position/list") // 관리자 페이지
    public ResponseEntity<CommonResponse> find_all() {

        List<PositionDTO> dtoList = positionService.positionList();

//        List<PositionDTO> dtoList = new ArrayList<>();
//        dtoList.add(PositionDTO.builder()
//                .positionName("사원")
//                .vacation("1")
//                .status(PositionStatus.ACTIVATION)
//                .build());
//        dtoList.add(PositionDTO.builder()
//                .positionName("대리")
//                .vacation("3")
//                .status(PositionStatus.ACTIVATION)
//                .build());

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // TODO: 직급 수정 (관리자) - 명칭, 직급별 휴가 수
    @Permission
    @PostMapping("/api/v1/position/modify/{id}") // 관리자 페이지
    public ResponseEntity<CommonResponse> modify(@PathVariable("id") String id, @RequestBody PositionModifyDTO dto) {

        PositionDTO position = positionService.positionModify(id, dto);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(position)
                .build());
    }

    // TODO: 직급 삭제 (관리자) - 안쓰는 직급 비활성화
    @Permission
    @PostMapping("/api/v1/position/delete/{id}") // 관리자 페이지
    public ResponseEntity<CommonResponse> delete(@PathVariable("id") String id) {

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(positionService.positionDelete(id))
                .build());
    }


}
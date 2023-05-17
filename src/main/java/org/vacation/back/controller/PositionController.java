package org.vacation.back.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.PositionDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;
import org.vacation.back.exception.*;
import org.vacation.back.service.PositionService;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PositionController {

    private final PositionService positionService;

    // 직급 추가
    @Permission
    @PostMapping("/api/v1/position/save")
    public ResponseEntity<CommonResponse<?>> save(@RequestBody PositionSaveDTO dto) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", dto.getPositionName());
        boolean vacationCheck = Pattern.matches("^[0-9]{1,2}$", dto.getVacation());

        if (!idCheck)
            throw new PositionNameCheckException("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        if (!vacationCheck)
            throw new PositionVacationCheckException("직급별 연차 일수는 1~2자리 숫자만 입력해 주세요.");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(positionService.positionSave(dto))
                .build());
    }

    // 직급 조회
    @Permission
    @GetMapping("/api/v1/position/detail/{id}")
    public ResponseEntity<CommonResponse<?>> detail(@PathVariable("id") String id) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", id);
        if (!idCheck)
            throw new PositionNameCheckException("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        PositionDTO dto = positionService.positionDetail(id);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build()
        );
    }

    // 직급 전체 조회
    @Permission
    @GetMapping("/api/v1/position/list")
    public ResponseEntity<CommonResponse<?>> find_all() {

        List<PositionDTO> dtoList = positionService.positionList();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dtoList)
                .build());
    }


    // 직급 수정
    @Permission
    @PostMapping("/api/v1/position/modify/{id}")
    public ResponseEntity<CommonResponse<?>> modify(@PathVariable("id") String id, @RequestBody PositionModifyDTO dto) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", id);
        boolean vacationCheck = Pattern.matches("^[0-9]{1,2}$", dto.getVacation());

        if (!idCheck)
            throw new PositionNameCheckException("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        if (!vacationCheck)
            throw new PositionVacationCheckException("직급별 연차 일수는 1~2자리 숫자만 입력해 주세요.");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(positionService.positionModify(id, dto))
                .build());
    }

    // 직급 삭제
    @Permission
    @PostMapping("/api/v1/position/delete/{id}")
    public ResponseEntity<CommonResponse<?>> delete(@PathVariable("id") String id) {

        boolean idCheck = Pattern.matches("^[a-zA-Z0-9가-힣&&[^ㄱ-ㅎㅏ-ㅣ]]{2,10}$", id);

        if (!idCheck)
            throw new PositionNameCheckException("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다.");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(positionService.positionDelete(id))
                .build());
    }

    @ExceptionHandler(PositionNameCheckException.class)
    public ResponseEntity<CommonResponse<?>> positionNameCheckException(PositionNameCheckException e) {
        System.out.println("직급명 정규식표현 체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.CHECK_POSITION_NAME)
                        .data(false));
    }


    @ExceptionHandler(PositionVacationCheckException.class)
    public ResponseEntity<CommonResponse<?>> positionVacationCheckException(PositionVacationCheckException e) {
        System.out.println("직급별 연차 일수 정규식표현 체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.CHECK_POSITION_VACATION)
                        .data(false));
    }

    @ExceptionHandler(PositionDuplicateException.class)
    public ResponseEntity<CommonResponse<?>> duplicatePosition(PositionDuplicateException e) {
        System.out.println("직급명 중복체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.EXIST_POSITION)
                        .data(false));
    }

    @ExceptionHandler(NotFoundPositionException.class)
    public ResponseEntity<CommonResponse<?>> positionNameException(NotFoundPositionException e) {
        System.out.println("직급명 조회체크 예외처리 : " + e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new CommonResponse<>(ErrorCode.NOT_FOUND_POSITION_NAME)
                        .data(false));
    }
}
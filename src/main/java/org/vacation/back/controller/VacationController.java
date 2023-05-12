package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vacation/")
public class VacationController {

    private final VacationService vacationService;

    @PostMapping("save")
    public ResponseEntity<CommonResponse> save(@Validated @RequestBody VacationSaveRequestDTO dto,
                                               HttpServletRequest request){
        vacationService.vacationSave(dto, request);


        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<CommonResponse> detail(
            @PathVariable(value = "id") Long id){

        VacationResponseDTO dto = vacationService.vacationDetail(id);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build());
    }


    @GetMapping(value = {"list/{month}", "list"})
    public ResponseEntity<CommonResponse> vacationList(@PathVariable(value = "month", required = false) String month){

        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<VacationResponseDTO> vacationResponseDTOList;
        if (month != null){
            if (!"0".equals(month)) { // month가 0일때 WAITING 상태인 data만 불러옴
                vacationResponseDTOList = vacationService.vacationListMonth(month);
            }else {
                vacationResponseDTOList = vacationService.vacationListStatus();
            }
        }else { // month가 없을 경우 이번달 정보만 가져오기
            int currentMonth = LocalDate.now().getMonthValue();
            vacationResponseDTOList = vacationService.vacationListMonth(String.valueOf(currentMonth));
        }
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTOList)
                .build());
    }


    @PostMapping("modify")
    public ResponseEntity<CommonResponse> modify(
            @RequestBody VacationModifyDTO dto){
        //TODO: 연차 시작일 or 끝나는일 수정 가능(추후 변경가능)
        if (dto.getStart() == null || dto.getEnd() == null) {
            throw new CommonException(ErrorCode.DTO_IS_NULL, "비어있는 입력이 있습니다.");
        }
        vacationService.vacationModify(dto);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id){

        vacationService.vacationDelete(id);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @Permission
    @PostMapping("ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){
        vacationService.vacationOk(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
    @Permission
    @PostMapping("rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){
        vacationService.vacationRejected(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
}

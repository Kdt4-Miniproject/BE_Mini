package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;
    @PostMapping("/api/v1/vacation/save")
    public ResponseEntity<CommonResponse> save(@RequestBody VacationSaveRequestDTO dto){
        vacationService.vacationSave(dto);
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
        VacationResponseDTO dto = vacationService.vacationDetail(id);
        //responseDTO 삭제 후 .data true로 변경 예정
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build());
    }

    @GetMapping("/api/v1/vacation/list")
    public ResponseEntity<CommonResponse> vacationList(
            HttpServletRequest request){
        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<VacationResponseDTO> vacationTempDTOList = vacationService.vacationList();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationTempDTOList)
                .build());
    }

    @PostMapping("/api/v1/vacation/modify/{id}")
    public ResponseEntity<CommonResponse> modify(
            @PathVariable(value = "id") Long id,
            @RequestBody VacationModifyDTO dto,
            HttpServletRequest request){
        //TODO: 연차 시작일 or 끝나는일 수정 가능(추후 변경가능)

        vacationService.vacationModify(id, dto);
        //responseDTO 삭제 후 .data true로 변경 예정
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)
                .build());
    }


    @PostMapping("/api/v1/vacation/delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){

        vacationService.vacationDelete(id);
        //responseDTO 삭제 후 .data true로 변경 예정
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)
                .build());
    }


    @PostMapping("/api/v1/vacation/ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){
        vacationService.vacationOk(id);
        //responseDTO 삭제 후 .data true로 변경 예정
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)
                .build());
    }

    @PostMapping("/api/v1/vacation/rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){
        vacationService.vacationRejected(id);
        //responseDTO 삭제 후 .data true로 변경 예정
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)
                .build());
    }
}

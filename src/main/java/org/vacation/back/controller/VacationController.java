package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;
    @PostMapping("/api/v1/vacation/save")
    public ResponseEntity<CommonResponse> save(@RequestBody VacationSaveRequestDTO dto,
                                               HttpServletRequest request){
        if (dto.getMemberName() == null || dto.getStart() == null || dto.getEnd() == null || dto.getStatus() == null) {
            throw new CommonException(ErrorCode.DTO_IS_NULL, "비어있는 입력이 있습니다.");
        }
        vacationService.vacationSave(dto, (String) request.getAttribute("username"));

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @GetMapping("/api/v1/vacation/detail/{id}")
    public ResponseEntity<CommonResponse> detail(
            @PathVariable(value = "id") Long id){
        //TODO: 조회하는 유저가 정보 확인
        VacationResponseDTO dto = vacationService.vacationDetail(id);
        //responseDTO 삭제 후 .data true로 변경 예정
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build());
    }

    @GetMapping("/api/v1/vacation/list")
    public ResponseEntity<CommonResponse> vacationList(){
        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<VacationResponseDTO> vacationTempDTOList = vacationService.vacationList();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationTempDTOList)
                .build());
    }

    @Permission
    @PostMapping("/api/v1/vacation/modify/{id}")
    public ResponseEntity<CommonResponse> modify(
            @PathVariable(value = "id") Long id,
            @RequestBody VacationModifyDTO dto){
        //TODO: 연차 시작일 or 끝나는일 수정 가능(추후 변경가능)
        if (dto.getStart() == null || dto.getEnd() == null) {
            throw new CommonException(ErrorCode.DTO_IS_NULL, "비어있는 입력이 있습니다.");
        }
        vacationService.vacationModify(id, dto);
        //responseDTO 삭제 후 .data true로 변경 예정
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)
                .build());
    }


    @Permission
    @PostMapping("/api/v1/vacation/delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id){

        vacationService.vacationDelete(id);
        //responseDTO 삭제 후 .data true로 변경 예정
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)
                .build());
    }

    @Permission
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
    @Permission
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

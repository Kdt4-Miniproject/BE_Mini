package org.vacation.back.controller;


import com.querydsl.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.domain.Vacation;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vacation/")
public class VacationController {

    private final VacationService vacationService;

    @PostMapping("save")
    public ResponseEntity<CommonResponse> save(@RequestBody VacationSaveRequestDTO dto,
                                               HttpServletRequest request){
        if (dto.getMemberUsername() == null || dto.getStart() == null || dto.getEnd() == null || dto.getStatus() == null) {
            throw new CommonException(ErrorCode.DTO_IS_NULL, "비어있는 입력이 있습니다.");
        }
        dto.setMemberUsername((String) request.getAttribute("username"));
        vacationService.vacationSave(dto);


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
    public ResponseEntity<CommonResponse> vacationList(@PathVariable(value = "month", required = false) Optional<String> month){
        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<VacationResponseDTO> vacationResponseDTOList;
        if (month.isPresent()){
            if (month.equals("0")) {
                vacationResponseDTOList = vacationService.vacationListStatus();
            }else {
                vacationResponseDTOList = vacationService.vacationListMonth(month.get());
            }
        }else {
            int currentMonth = LocalDate.now().getMonthValue();
            vacationResponseDTOList = vacationService.vacationListMonth(String.valueOf(currentMonth));
        }
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTOList)
                .build());
    }


    @PostMapping("modify/{id}")
    public ResponseEntity<CommonResponse> modify(
            @PathVariable(value = "id") Long id,
            @RequestBody VacationModifyDTO dto){
        //TODO: 연차 시작일 or 끝나는일 수정 가능(추후 변경가능)
        if (dto.getStart() == null || dto.getEnd() == null) {
            throw new CommonException(ErrorCode.DTO_IS_NULL, "비어있는 입력이 있습니다.");
        }
        vacationService.vacationModify(id, dto);
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);//responseDTO 삭제 후 .data true로 변경 예정
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                //responseDTO 삭제 후 .data true로 변경 예정
                .data(vacationResponseDTO)
                .build());
    }

    @PostMapping("delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id){

        vacationService.vacationDelete(id);

        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);//responseDTO 삭제 후 .data true로 변경 예정
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)//responseDTO 삭제 후 .data true로 변경 예정
                .build());
    }

    @Permission
    @PostMapping("ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){
        vacationService.vacationOk(id);
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);//responseDTO 삭제 후 .data true로 변경 예정
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)//responseDTO 삭제 후 .data true로 변경 예정
                .build());
    }
    @Permission
    @PostMapping("rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){
        vacationService.vacationRejected(id);
        VacationResponseDTO vacationResponseDTO = vacationService.vacationDetail(id);//responseDTO 삭제 후 .data true로 변경 예정
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(vacationResponseDTO)//responseDTO 삭제 후 .data true로 변경 예정
                .build());
    }
}

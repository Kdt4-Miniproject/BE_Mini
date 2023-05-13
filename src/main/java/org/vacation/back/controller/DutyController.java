package org.vacation.back.controller;

import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.DutyDTO;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyResponseDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.service.DutyService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/duty/")
public class DutyController {
    public final DutyService dutyService;

    @PostMapping("save")
    public ResponseEntity<CommonResponse> save(
            @RequestBody DutySaveRequestDTO dutySaveRequestDTO, HttpServletRequest request){

        if(dutySaveRequestDTO.getUsername() == null || dutySaveRequestDTO.getDay() == null){
            throw new CommonException(ErrorCode.DTO_IS_NULL, "입력을 완료하세요");
        }

        dutySaveRequestDTO.setUsername((String) request.getAttribute("username"));
        dutyService.dutySave(dutySaveRequestDTO);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<CommonResponse> detail(
            @PathVariable(value = "id") Long id){

        DutyResponseDTO dutyResponseDTO = dutyService.dutyDetail(id);


        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dutyResponseDTO)
                .build());
    }


    @GetMapping({"list/{month}","list"})
    public ResponseEntity<CommonResponse> dutyList(@PathVariable(value = "month", required = false) String month){
        //TODO: 조회하는 유저가 권한 확인 (권한 별로 정보 뿌리기)
        List<DutyResponseDTO> dutyResponseDTOList;
        if(month != null){
            if(!"0".equals(month)) {
                dutyResponseDTOList = dutyService.dutyListMonth(month);

            }else{
                dutyResponseDTOList = dutyService.dutyListStatus();
            }
        }else{
            int currentMonth = LocalDate.now().getMonthValue();
            dutyResponseDTOList = dutyService.dutyListMonth(String.valueOf(currentMonth));
        }

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dutyResponseDTOList)
                .build());
    }


    @PostMapping("modify")
    public ResponseEntity<CommonResponse> modify(
            @RequestBody DutyModifyDTO dutyModifyDTO){
        //TODO: 맞 트레이드 하는 로직
        if (dutyModifyDTO.getDay() == null) {
            throw new CommonException(ErrorCode.DTO_IS_NULL, "입력을 완료하세요");
        }

        dutyService.dutyModify(dutyModifyDTO);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }


    @PostMapping("delete/{id}")
    public ResponseEntity<CommonResponse> delete(
            @PathVariable(value = "id") Long id){

        dutyService.dutyDelete(id);

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
    @Permission
    @PostMapping("ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){

        dutyService.dutyOk(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }
    @Permission
    @PostMapping("rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){

        dutyService.dutyRejected(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    //당직 임의 배정
    @Permission
    @GetMapping("assign")
    public ResponseEntity<CommonResponse> assign() {

        List<DutyResponseDTO> dutyResponseDTO = dutyService.dutyAssign();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dutyResponseDTO)
                .build());

    }
}

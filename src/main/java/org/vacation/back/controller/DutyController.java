package org.vacation.back.controller;

import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.service.DutyService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @ExceptionHandler(CommonException.class)
    @PostMapping("save")
    public ResponseEntity<CommonResponse> save(
            @Valid @RequestBody DutySaveRequestDTO dutySaveRequestDTO, HttpServletRequest request){


        dutyService.dutySave(dutySaveRequestDTO, request);

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
    public ResponseEntity<CommonResponse> dutyList(@PathVariable(value = "month", required = false) String month,
                                                   @RequestParam(name = "page", defaultValue = "0")int page,
                                                   @RequestParam(name = "size", defaultValue = "10")int size){

        PageRequest pageable = PageRequest.of(page, size);

        Page<DutyResponseDTO> dutyPage;

        if(month != null){
            if(!"0".equals(month)) {
                dutyPage =  dutyService.dutyListMonth(month, pageable);

            }else{
                dutyPage = dutyService.dutyListStatus(pageable);
            }
        }else{
            int currentMonth = LocalDate.now().getMonthValue();
            dutyPage = dutyService.dutyListMonth(String.valueOf(currentMonth), pageable);
        }

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dutyPage)
                .build());
    }


    @PostMapping("modify")
    public ResponseEntity<CommonResponse> modify(
            @RequestBody DutyModifyDTO dutyModifyDTO){


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

    @ExceptionHandler(dutyException.class)
    public ResponseEntity<CommonResponse<?>> dutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.OVERD_VACATION)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(AlreadyDutyException.class)
    public ResponseEntity<CommonResponse<?>> alreadyDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.ALREADY_DUTY)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(NotFoundDutyException.class)
    public ResponseEntity<CommonResponse<?>> notFoundId() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }
}

}

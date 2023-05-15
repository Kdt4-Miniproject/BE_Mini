package org.vacation.back.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.response.DutyResponseDTO;
import org.vacation.back.dto.response.PageResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.service.DutyService;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/duty/")
public class DutyController {
    public final DutyService dutyService;

    @ExceptionHandler(CommonException.class)
    @PostMapping("save")
    public ResponseEntity<CommonResponse> save(
            @Valid @RequestBody DutySaveRequestDTO dutySaveRequestDTO){


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
    public ResponseEntity<CommonResponse> dutyList(@PathVariable(value = "month", required = false) String month,
                                                   @RequestParam(name = "page", defaultValue = "0")int page,
                                                   @RequestParam(name = "size", defaultValue = "10")int size){

        PageRequest pageable = PageRequest.of(page, size);

        Page<DutyResponseDTO> dutyPage;
        PageResponseDTO <?> pageResponseDTO;

        if(month != null){
            if(!"0".equals(month)) {
                dutyPage =  dutyService.dutyListMonth(month, pageable);
                pageResponseDTO = PageResponseDTO.builder()
                        .first(dutyPage.isFirst())
                        .last(dutyPage.isLast())
                        .content(dutyPage.getContent())
                        .total(dutyPage.getTotalElements())
                        .build();

            }else{
                dutyPage = dutyService.dutyListStatus(pageable);
                pageResponseDTO = PageResponseDTO.builder()
                        .first(dutyPage.isFirst())
                        .last(dutyPage.isLast())
                        .content(dutyPage.getContent())
                        .total(dutyPage.getTotalElements())
                        .build();
            }
        }else{
            int currentMonth = LocalDate.now().getMonthValue();
            dutyPage = dutyService.dutyListMonth(String.valueOf(currentMonth), pageable);
            pageResponseDTO = PageResponseDTO.builder()
                    .first(dutyPage.isFirst())
                    .last(dutyPage.isLast())
                    .content(dutyPage.getContent())
                    .total(dutyPage.getTotalElements())
                    .build();
        }

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(pageResponseDTO)
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
    @Permission
    @GetMapping("assign")
    public ResponseEntity<CommonResponse> assign(){

        dutyService.dutyAssign();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }


    @ExceptionHandler(DutyMemberNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> dutyMemberNotFoundException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.NOTFOUND_DUTY)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(PastDateForDutyException.class)
    public ResponseEntity<CommonResponse<?>> pastDateForDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.PASTDATE_DUTY)
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
                        .codeEnum(CodeEnum.UNREGISTERDE_DUTY)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<CommonResponse<?>> alreadyDeletedDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.ALREADY_DELETED_DUTY)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(AlreadyOkException.class)
    public ResponseEntity<CommonResponse<?>> alreadyOkDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.ALREADY_OK_DUTY)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(AlreadyRejectedException.class)
    public ResponseEntity<CommonResponse<?>> alreadyRejectedDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.ALREADY_REJECTED_DUTY)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(AlreadyModifyException.class)
    public ResponseEntity<CommonResponse<?>> alreadyModifyDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.ALREADY_MODIFY_DUTY)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(DuplicatedDutyException.class)
    public ResponseEntity<CommonResponse<?>> duplicatedDutyException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.DUPLICATED_DUTY)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(IntialDutyException.class)
    public ResponseEntity<CommonResponse<?>> intialDutyException() {
         CommonResponse<?> commonResponse = CommonResponse
                 .builder()
                 .codeEnum(CodeEnum.NOT_FOUND)
                 .data(false)
                 .build();
        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }
}


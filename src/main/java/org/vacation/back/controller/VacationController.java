package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.AdminAndLeader;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.response.PageResponseDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.exception.AlreadyVacationException;
import org.vacation.back.exception.NotFoundVacationException;
import org.vacation.back.exception.OveredVacationException;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vacation/")
public class VacationController {

    private final VacationService vacationService;

    @PostMapping("save")
    public ResponseEntity<CommonResponse> save(@Valid @RequestBody VacationSaveRequestDTO dto,
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
    public ResponseEntity<CommonResponse> vacationList(@PathVariable(value = "month", required = false) String month,
                                                       @RequestParam(name = "page", defaultValue = "0")int page,
                                                       @RequestParam(name = "size", defaultValue = "10")int size){
        PageRequest pageable = PageRequest.of(page, size);

        Page<VacationResponseDTO> vacationPage;
        PageResponseDTO<?> pageResponseDTO;

        if (month != null){
            if (!"0".equals(month)) { // month가 0일때 WAITING 상태인 data만 불러옴
                vacationPage = vacationService.vacationListMonth(month, pageable);
                pageResponseDTO = PageResponseDTO.builder()
                        .first(vacationPage.isFirst())
                        .last(vacationPage.isLast())
                        .content(vacationPage.getContent())
                        .total(vacationPage.getTotalElements())
                        .build();
            }else {
                List<VacationResponseDTO> vacationListStatus = vacationService.vacationListStatus();

                return ResponseEntity.ok(CommonResponse.builder()
                                .codeEnum(CodeEnum.SUCCESS)
                                .data(vacationListStatus)
                        .build());
            }
        }else { // month가 없을 경우 이번달 정보만 가져오기
            int currentMonth = LocalDate.now().getMonthValue();
            vacationPage = vacationService.vacationListMonth(String.valueOf(currentMonth), pageable);
            pageResponseDTO = PageResponseDTO.builder()
                    .first(vacationPage.isFirst())
                    .last(vacationPage.isLast())
                    .content(vacationPage.getContent())
                    .total(vacationPage.getTotalElements())
                    .build();
        }


        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(pageResponseDTO)
                .build());
    }


    @PostMapping("modify")
    public ResponseEntity<CommonResponse> modify(
            @Valid @RequestBody VacationModifyDTO dto){
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

    @AdminAndLeader
    @PostMapping("ok/{id}")
    public ResponseEntity<CommonResponse> ok(
            @PathVariable(value = "id") Long id){
        vacationService.vacationOk(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @AdminAndLeader
    @PostMapping("updateok/{id}")
    public ResponseEntity<CommonResponse> updateOk(
            @PathVariable(value = "id") Long id){
        vacationService.vacationOk(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @AdminAndLeader
    @PostMapping("rejected/{id}")
    public ResponseEntity<CommonResponse> rejected(
            @PathVariable(value = "id") Long id){
        vacationService.vacationRejected(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @AdminAndLeader
    @PostMapping("updaterejected/{id}")
    public ResponseEntity<CommonResponse> updateRejected(
            @PathVariable(value = "id") Long id){
        vacationService.vacationRejected(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build());
    }

    @ExceptionHandler(OveredVacationException.class)
    public ResponseEntity<CommonResponse<?>> overedVacationException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.OVERD_VACATION)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(AlreadyVacationException.class)
    public ResponseEntity<CommonResponse<?>> alreadyVacationException() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.ALREADY_VACATION)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(NotFoundVacationException.class)
    public ResponseEntity<CommonResponse<?>> notFoundId() {
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }
}

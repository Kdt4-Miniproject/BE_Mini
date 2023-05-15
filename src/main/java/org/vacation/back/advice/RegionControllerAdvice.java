package org.vacation.back.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;

@RestControllerAdvice
public class RegionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        // 에러 처리 로직 작성
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .codeEnum(CodeEnum.VALID_FAILE)
                .data(false)
                .build();
        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }
}

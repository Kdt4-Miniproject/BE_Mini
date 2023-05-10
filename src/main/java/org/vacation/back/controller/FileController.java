package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.common.FileDTO;
import org.vacation.back.common.FileResponseData;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.exception.FileNotFoundException;
import org.vacation.back.exception.FileUploadFailException;
import org.vacation.back.exception.PasswordNotMatchException;
import org.vacation.back.exception.S3UploadException;
import org.vacation.back.service.FileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;


    @GetMapping("/view")
    public ResponseEntity<Resource> viewFile(@RequestParam String fileName){
        FileResponseData data = fileService.viewFile(fileName);
        if(ObjectUtils.isEmpty(data)){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(data.getHeaders()).body(data.getResource());
    }

    @PostMapping("/api/v1/upload")
    public ResponseEntity<CommonResponse<?>> upload(FileDTO fileDTO){

        List<String> list = fileService.upload(fileDTO);

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(list.isEmpty() ? list.stream().findFirst() : null)
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }


    @PostMapping(value = "/api/v1/temp/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<?>> tempUpload(FileDTO fileDTO){

        List<String> list = fileService.tempUpload(fileDTO);

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(list.stream().findFirst())
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);

    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> fileNotFound(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(FileUploadFailException.class)
    public ResponseEntity<CommonResponse<?>> fileUploadFail(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }


    @ExceptionHandler(S3UploadException.class)
    public ResponseEntity<CommonResponse<?>> s3UploadFail(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }
}

package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.RegisterMemberDTO;

@RestController
@RequiredArgsConstructor
public class MemberController {


    @GetMapping("/api/v1/join")
    public ResponseEntity<CommonResponse> test(@RequestBody RegisterMemberDTO registerMemberDTO){

        //TODO: 파라미터에서 Valid가 필요하고,
        //TODO: 등록시 실패 Exception 처리가 필요하고,
        //TODO: 해당 username이 PK인지 체크 조회,
        //TODO: Exception은 ExceptionHandler로 처리할 것임

        return ResponseEntity.ok(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(true)
                .build());
}
}

package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.dto.request.member.LoginRequestDTO;
import org.vacation.back.dto.request.member.MemberModifyDTO;
import org.vacation.back.dto.request.member.RegisterMemberDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {


    @GetMapping("/api/v1/member/search")
    public ResponseEntity<CommonResponse> page(){
        return null;
    }

    @GetMapping("/api/v1/member/detail")
    public ResponseEntity<CommonResponse> detail(@RequestHeader("Authorization") String token){
        //TODO: 토큰 디코딩이 필요하다. 디코딩해서 username을 데이터 조회하는데 사용한다.
        //TODO: 토큰 만료 상태인지 그런거 체크 필요

        log.info(token);

        MemberDTO dto = MemberDTO.builder()
                .username("admin")
                .role(Role.ADMIN)
                .birthDate("2023-04-28")
                .email("admin@naver.com")
                .years("15")
                .employeeNumber("202304281234")
                .updatedAt(LocalDateTime.now())
                .phoneNumber("010-1234-1234")
                .name("김독자")
                .fileName(UUID.randomUUID().toString()+"_data.jpg")
                .build();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(dto)
                .build());

    }


    /**
     * TODO: API 설명 작성예정
     * */
    @PostMapping("/api/v1/join")
    public ResponseEntity<CommonResponse> join(@RequestBody RegisterMemberDTO registerMemberDTO){

        //TODO: 파라미터에서 Valid가 필요하고,
        //TODO: 등록시 실패 Exception 처리가 필요하고,
        //TODO: 해당 username이 PK인지 체크 조회,
        //TODO: Exception은 ExceptionHandler로 처리할 것임

        return ResponseEntity.ok(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(true)
                .build());
    }

    /**
     * TODO: API 설명 작성예정
     * */
    @PostMapping("/api/v1/loginTest")
    public ResponseEntity<CommonResponse> login(@RequestBody LoginRequestDTO loginRequestDTO){
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcm5hbWUiOiJhZG1pbiIsImlhdCI6MTUxNjIzOTAyMn0." +
                "lI7kQCfzQVDjl5WZaApceqqWwlEsKbL4-ECONjArLBE";
        /*
        * TODO: 실제 들어온 username, password Provider를 직접 구현할 거임
        * TODO: 들어온 username, password Validation 체크가 필요하다.
        *  TODO: token에 username, name, role, imageName
        * */

        return ResponseEntity.ok()
                .header("Authorization","Bearer "+jwt)
                .header("X-Refresh-Token",jwt)
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(true).build());
    }

    /**
     * TODO: API 설명 작성예정
     * */
    @PostMapping("/api/v1/member/modify")
    public ResponseEntity<CommonResponse> modify(
            @RequestBody MemberModifyDTO memberModifyDTO,
            HttpServletRequest request
            ){

         //   log.info("{}",request.getAttribute("username"));
        /* TODO: 토큰에 username과 수정할 데이터에 username이 같은지, 또는 관리자인지
           TODO: 현재 바꿀 수 있는 데이터는 phoneNumber, employeeNumber, year 3가지인데 더 바뀔 수도 있음
         * TODO: 더티체킹으로 해당 3가지 값으로 수정할 예정
         * TODO: 3가지 값만 바꿀 수 있도록 메소드를 만들 예정 status는 String으로 받아서
         *      Enum으로 컨버팅할 예정임
         * */
        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(true).build());
    }




}

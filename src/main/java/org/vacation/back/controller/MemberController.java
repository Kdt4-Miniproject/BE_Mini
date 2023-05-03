package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Permission;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.dto.request.member.*;
import org.vacation.back.dto.response.PageResponseDTO;
import org.vacation.back.exception.EmailNotValidException;
import org.vacation.back.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * TODO: API 설명 작성예정
     * */
    @GetMapping("/api/v1/join/check")
    public ResponseEntity<CommonResponse<?>> checking(@RequestParam String username){
        // TODO: PathVariable로 들어온 username이 PK로 겹치는게 있는지 확인한다.

        if(!isValidEmail(username)) throw new EmailNotValidException("Invalid email format");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data( memberService.exist(username))
                .build());
    }

    /**
     * TODO: API 설명 작성예정
     * */
    @GetMapping("/api/v1/member/page/search")
    public ResponseEntity<CommonResponse<?>> page(@RequestParam(required = false) String text,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){

        MemberDTO dto = MemberDTO.builder()
                .username("admin")
                .role(Role.ADMIN)
                .birthDate("2023-04-28")
                .email("admin@naver.com")
                .years(15)
                .employeeNumber("202304281234")
                .updatedAt(LocalDateTime.now())
                .phoneNumber("010-1234-1234")
                .name("김독자")
                .fileName(UUID.randomUUID().toString()+"_data.jpg")
                .build();

        MemberDTO dto2 = MemberDTO.builder()
                .username("admin")
                .role(Role.ADMIN)
                .birthDate("2023-04-28")
                .email("admin@naver.com")
                .years(15)
                .employeeNumber("202304281234")
                .updatedAt(LocalDateTime.now())
                .phoneNumber("010-1234-1234")
                .name("김독자")
                .fileName(UUID.randomUUID().toString()+"_data.jpg")
                .build();

        List<MemberDTO> list = new ArrayList<>();
        list.add(dto);
        list.add(dto2);

        PageResponseDTO<?> pageResponseDTO = PageResponseDTO.builder()
                .total(2)
                .first(true)
                .last(false)
                .content(list)
                .build();

         return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(pageResponseDTO)
                .build());
    }
    /**
     * TODO: API 설명 작성예정
     * */
    @GetMapping("/api/v1/member/detail")
    public ResponseEntity<CommonResponse<?>> detail(@RequestHeader("Authorization") String token){
        //TODO: 토큰 디코딩이 필요하다. 디코딩해서 username을 데이터 조회하는데 사용한다.
        //TODO: 토큰 만료 상태인지 그런거 체크 필요

        log.info(token);

        MemberDTO dto = MemberDTO.builder()
                .username("admin")
                .role(Role.ADMIN)
                .birthDate("2023-04-28")
                .email("admin@naver.com")
                .years(15)
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
    public ResponseEntity<CommonResponse<?>> join(@RequestBody RegisterMemberDTO registerMemberDTO){

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
    public ResponseEntity<CommonResponse<?>> login(@RequestBody LoginRequestDTO loginRequestDTO){
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
    public ResponseEntity<CommonResponse<?>> modify(
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

    @PostMapping("/api/v1/member/pwd/modify")
    public ResponseEntity<CommonResponse<?>> pwdModify(
            @RequestBody PasswordModifyRequest passwordModifyRequest,
            HttpServletRequest request
    ){
        /*
        * TODO: request로 토큰을 조회하여 해당 토큰에 사용자의 정보를 조회하고 들어온 PWD로 변경한다
        * */

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(true).build());
    }

    /**
     * 추가 적인 API 설명 그리고 어떻게 구성할건지 작성필요 그리고 해당 API는 관리자권한으로 사용자의 모든 걸 수정한다.
     *
     * */
    @Permission
    @PostMapping("/api/v1/member/admin/modify")
    public ResponseEntity<CommonResponse<?>> adminMNodify(
            @RequestBody AdminMemberModifyRequest adminMemberModifyRequest,
            HttpServletRequest request){

        /*
         들어온 데이터에 username으로 사용자를 조회하여 해당 사용자를 다른 모든 데이터로 변환할 예정임
        * 성공시 아래 값을 리턴하고 실패시 다른 값을 리턴하도록 할 예쩡임
        * 아직 코드는 만드지 않았지만 만들예정
        * */

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(true)
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }
    @Permission
    @PostMapping("/api/v1/member/admin/role/modify")
    public ResponseEntity<CommonResponse<?>> roleChange(@RequestBody RoleChangeRequest request){

        /*
        * TODO: request에 Username을 통해 해당 데이터를 조회하고 데이터가 존재하고 해당 데이터의 값을 권한을 확인한 후 들어온 role이랑 다르면 수정
        *
        * */

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(true)
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }


    /*
    *   이메일 주소는 @ 기호를 포함해야 합니다.
        @ 기호 앞의 문자열은 영문자, 숫자, 밑줄, 더하기 기호, 별표, 마이너스 기호를 포함할 수 있습니다.
        @ 기호 뒤의 도메인 이름은 영문자, 숫자, 하이픈으로 이루어진 문자열이어야 하며, 점(.)으로 구분된 최소한 하나 이상의 레이블을 포함해야 합니다.
        도메인 이름의 마지막 레이블은 최소한 2자 이상의 영문자로 이루어져야 합니다.
    * */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<CommonResponse<?>> exception(EmailNotValidException ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                .build());
    }


}

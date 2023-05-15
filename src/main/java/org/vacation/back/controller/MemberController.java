package org.vacation.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.vacation.back.annotation.Leader;
import org.vacation.back.annotation.Permission;
import org.vacation.back.common.Search;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.dto.request.member.*;
import org.vacation.back.dto.response.PageResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.service.MemberService;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final VacationService vacationService;

    /**
     * @apiNote  username 중복을 체크한다.
     * */
    @GetMapping("/api/v1/join/check")
    public ResponseEntity<CommonResponse<?>> checking(@RequestParam String username){
        // TODO: PathVariable로 들어온 username이 PK로 겹치는게 있는지 확인한다.

    //    if(!isValidEmail(username)) throw new EmailNotValidException("Invalid email format");

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data( memberService.exist(username))
                .build());
    }
    /**
     * @apiNote 검색
     * */
    @GetMapping("/api/v1/member/page/search")
    public ResponseEntity<CommonResponse<?>> page(@RequestParam(defaultValue = "ALL") Search text,
                                               @RequestParam(required = false) String keyword,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){


        PageResponseDTO<?> pageResponseDTO = memberService.pageMember(text,keyword,page,size);

         return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(pageResponseDTO)
                .build());
    }
    /**
     * @apiNote 자세히보기
     * */
    @GetMapping("/api/v1/member/detail")
    public ResponseEntity<CommonResponse<?>> detail(HttpServletRequest request){
        //TODO: 토큰 디코딩이 필요하다. 디코딩해서 username을 데이터 조회하는데 사용한다.
        //TODO: 토큰 만료 상태인지 그런거 체크 필요
        String username = request.getAttribute("username").toString();

        return ResponseEntity.ok(CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(memberService.findByDetail(username))
                .build());

    }


    /**
     * TODO: API 설명 작성예정
     * */
    @PostMapping("/api/v1/join")
    public ResponseEntity<CommonResponse<?>> join(@RequestBody @Valid RegisterMemberDTO registerMemberDTO){

        return ResponseEntity.ok(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(memberService.join(registerMemberDTO))
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

        String username = request.getAttribute("username").toString();

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(memberService.memberModify(memberModifyDTO,username))
                        .build());
    }

    @Permission
    @PostMapping("/api/v1/member/admin/modify")
    public ResponseEntity<CommonResponse<?>> modifyAdmin(
            @RequestBody AdminMemberModifyRequest adminMemberModifyRequest
    ){


        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(memberService.adminModify(adminMemberModifyRequest))
                        .build());
    }



    @PostMapping("/api/v1/member/pwd/modify")
    public ResponseEntity<CommonResponse<?>> pwdModify(
            @RequestBody PasswordModifyRequest passwordModifyRequest,
            HttpServletRequest request
    ){
        String username = request.getAttribute("username").toString();

        boolean result = memberService.changePwd(username,passwordModifyRequest);

        return ResponseEntity.ok()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.SUCCESS)
                        .data(result)
                        .build());
    }

    /**
     * 추가 적인 API 설명 그리고 어떻게 구성할건지 작성필요 그리고 해당 API는 관리자권한으로 사용자의 모든 걸 수정한다.
     *
     * */

    @Permission
    @PostMapping("/api/v1/member/admin/role/modify")
    public ResponseEntity<CommonResponse<?>> roleChange(@RequestBody RoleChangeRequest request){
        /*
        * TODO: request에 Username을 통해 해당 데이터를 조회하고 데이터가 존재하고 해당 데이터의 값을 권한을 확인한 후 들어온 role이랑 다르면 수정
        * */
        boolean result = memberService.adminRoleModify(request);


        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(result)
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }

    @Permission
    @PostMapping("/api/v1/member/admin/active")
    public ResponseEntity<CommonResponse<?>> activate(@RequestBody AdminStatusModifyRequest request){

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(memberService.adminStatusModify(request))
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);

    }
    @Permission
    @GetMapping("/api/v1/member/admin/deactivation/list")
    public ResponseEntity<CommonResponse<?>> deactivationList(@RequestParam(defaultValue = "ALL") Search text,
                                                              @RequestParam(required = false) String keyword,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size){

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(memberService.deactivatedList(text, keyword, page, size))
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);

    }

    @Permission
    @PostMapping("/api/v1/member/admin/remove")
    public ResponseEntity<CommonResponse<?>> memberRemove(@RequestBody MemberRemoveRequest request){

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(memberService.memberRemove(request.getUsername()))
                .codeEnum(CodeEnum.SUCCESS)
                .build();

        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);

    }

    @Leader
    @GetMapping("/api/v1/member/vacation")
    public ResponseEntity<CommonResponse<?>> departmentMVacation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ,HttpServletRequest request){

        String departmentName = request.getAttribute("department").toString();

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .data(memberService.vacationFindByDepartment(PageRequest.of(page,size), departmentName))
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

    @ExceptionHandler(NotFoundPositionException.class)
    public ResponseEntity<CommonResponse<?>> positionException(){
            return ResponseEntity
                    .badRequest()
                    .body(CommonResponse.builder()
                            .codeEnum(CodeEnum.INVALID_ARGUMENT)
                            .data(false)
                            .build());
    }
    @ExceptionHandler(NotFoundDepartmentException.class)
    public ResponseEntity<CommonResponse<?>> departmentException(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(JoinFailException.class)
    public ResponseEntity<CommonResponse<?>> joinException(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<CommonResponse<?>> memberException(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<CommonResponse<?>> passwordException(){
        return ResponseEntity
                .badRequest()
                .body(CommonResponse.builder()
                        .codeEnum(CodeEnum.INVALID_ARGUMENT)
                        .data(false)
                        .build());
    }


}

package org.vacation.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.Search;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Position;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.*;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;

import static org.springframework.http.RequestEntity.head;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("회원 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MemberControllerTest extends MyWithRTestDoc {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6Iuq5gOuPheyekCIsImV4cCI6MTY4NTk0MzgyOSwidXNlcm5hbWUiOiJhZG1pbjIifQ.dpoyPuNUNl-JKFOvUMf52MxtpwRQfpPSgFRvf2L9c9JLazO0Xn9oRAQuc7EkKmZvfbWxAInEDn2horaW2XlxZQ";


    @BeforeEach
    void login_before(){
        departmentRepository.save(Department.builder()
                .departmentName("개발")
                .departmentPersonal(10)
                .build());
        Department department = departmentRepository.save(Department.builder()
                .departmentName("인사")
                .departmentPersonal(10)
                .build());
        departmentRepository.save(Department.builder()
                .departmentName("마케팅")
                .departmentPersonal(10)
                .build());

        Position position = positionRepository.save(Position.builder()
                .positionName("대리")
                .vacation("40")
                .build());

        memberRepository.save(Member.builder()
                .username("admin")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .department(department)
                .position(position)
                .name("관리자")
                .birthdate("2022-33-12")
                .email("test@naver.com")
                .employeeNumber("20221234")
                .memberStatus(MemberStatus.ACTIVATION)
                .phoneNumber("010-1234-1234")
                .build());

        memberRepository.save(Member.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .birthdate("2022-33-12")
                .department(department)
                .position(position)
                .name("유저")
                .email("test@naver.com")
                .employeeNumber("20221235")
                .phoneNumber("010-1234-1234")
                .memberStatus(MemberStatus.ACTIVATION)
                .build());
    }


    @Test
    @DisplayName("성공시")
    void member_username_checking() throws Exception {
        // given
        // when
        ResultActions resultActions =  mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/v1/join/check")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .param("username","test@naver.com")
                )
                .andExpect(status().isOk());
        // then
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    @Test
    @DisplayName("실패시")
    void member_username_checking_failure() throws Exception {
        // given
        // when
        ResultActions resultActions =  mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/v1/join/check")
                        .param("username","jaewoo")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest());
        // then
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/member/page/search")
    void member_page_request() throws Exception {
        // given

        // when
        ResultActions resultActions =  mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/v1/member/page/search")
                        .param("text","EMAIL")
                        .param("keyword","test")
                        .param("page", "0")
                        .param("size","10")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",token)
                )
                .andExpect(status().isOk());
        // then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andExpect(jsonPath("$.data.first").value(true));
        resultActions.andExpect(jsonPath("$.data.last").value(false));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }


    @Test
    @DisplayName("/api/v1/join")
    void member_register() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.UNAUTHORIZED).build();

        RegisterMemberDTO dto = RegisterMemberDTO.builder()
                .username("admin23")
                .password("1234")
                .birthDate("2023-04-26")
                .phoneNumber("010-1234-1234")
                .positionName("대리")
                .departmentName("개발")
                .fileName("404.jpg")
                .name("김독자")
                .joiningDay("2020-01-01")
                .years("3")
                .email("test@naver.com")
                .build();



       ResultActions resultActions =  mockMvc
               .perform(RestDocumentationRequestBuilders.post("/api/v1/join")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(objectMapper.writeValueAsString(dto))
               )
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
            // when
        // then
    }

    @Test
    @DisplayName("")
    void member_refresh_token() throws Exception {
        // given

        String accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjgzMjYzNTAzLCJ1c2VybmFtZSI6ImFkbWluIn0.hlUHLUliGMVUJXcVqj_N6lmpMb20ukgfBG_xQFuEFOA7_oUpF7ZZofFNSLhIxm9itefxGP6U6TT4gXA4Or_Z-w";
        String refreshToekn = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjg1NzY4ODI3LCJ1c2VybmFtZSI6ImFkbWluIn0.JEaYSN7oiP0z59dwZRMRbnqkhTlZ139Ieo5oT7UDXjNCMB2ZfRL-Qy2rrUb8DaAVtfbe5y5wUfvGA_Kp6DBNRQ";
        // when
        ResultActions resultActions =  mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/refresh")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",accessToken)
                        .header("X-Auth-Refresh-Token",refreshToekn)
                )
                .andExpect(status().isOk());

        Assertions.assertNotNull(resultActions.andReturn().getResponse().getHeader("X-Auth-Refresh-Token"));
        Assertions.assertNotNull(resultActions.andReturn().getResponse().getHeader("Authorization"));
        Assertions.assertNotEquals(resultActions.andReturn().getResponse().getHeader("Authorization"),accessToken);
        Assertions.assertEquals(resultActions.andReturn().getResponse().getHeader("X-Auth-Refresh-Token"),refreshToekn);

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        // then
    }
    
    @Test
    @DisplayName("/api/v1/login")
    void member_login_success() throws Exception {
        // given
        LoginRequestDTO dto = LoginRequestDTO.builder()
                .username("admin")
                .password("1234")
                .build();
        // when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/login")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));

        resultActions.andExpect(header().exists(HttpHeaders.AUTHORIZATION));
        resultActions.andExpect(header().exists("X-Auth-Refresh-Token"));


        System.out.println(resultActions.andReturn().getResponse().getHeader("Authorization"));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }


    @Test
    @DisplayName("/api/v1/member/modify")
    void member_dto_modify() throws Exception {
        // given
        MemberModifyDTO dto  = MemberModifyDTO.builder()
                .email("test@nate.com")
                .name("김사랑22")
                .phoneNumber("01012344321")
                .fileName("404321.jpg")
                .newPassword("12340012")
                .oldPassword("123400")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/member/modify")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization",token)
        ).andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        // then
    }

    @Test
    @DisplayName("/api/v1/member/admin/modify")
    void member_modify_admin() throws Exception {
        AdminMemberModifyRequest request = AdminMemberModifyRequest.builder()

                .username("admin2")
                .name("나는야")
                .name("email@test.com")
                .phoneNumber("010-2222-2222")
                .birthDate("1111-11-11")
                .joiningDay("2022-01-01")
                .fileName("10111.jpg")
                .build();
        // when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/member/admin/modify")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .header("Authorization",token)
        ).andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
    
    
    @Test
    @DisplayName("")
    void member_detail_info() throws Exception {
        // given
        // when
        ResultActions resultActions =  mockMvc
                .perform(RestDocumentationRequestBuilders.get("/api/v1/member/detail")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",token)
                )
                .andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.status").value(200));

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
        // then
    }

    /**
     * @apiNote 로그인한 유저(토큰을 보유한) 해당 유저가 해당 API로 password 값을 보내면 해당 토큰에 있는 유저에 대한 비밀번호를 수정할 수 있다.
     *
     * */
    @Test
    @DisplayName("")
    void member_pwd_modify() throws Exception {
        // given
        PasswordModifyRequest request = PasswordModifyRequest.builder()
                .oldPassword("1234")
                .newPassword("123411")
                .build();

        // when
        ResultActions resultActions = mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/member/pwd/modify")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk());

        resultActions.andExpect(jsonPath("$.status").value(200));
        // then
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }




    /**
     * @apiNote 관리자 권한으로는 사용자를 찾아 권한을 수정한다.
     * */
    @Test
    @DisplayName("")
    void member_role_change_admin() throws Exception{
        // given
        RoleChangeRequest request = RoleChangeRequest.builder()
                .username("user")
                .role(Role.ADMIN)
                .build();

        // when
        ResultActions resultActions = mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/member/admin/role/modify")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk());
        // then
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    /**
     * @apiNote 권한없는 사용자가 권한을 변경하려고 시도한다.
     * */
    @Test
    @DisplayName("")
    void member_role_change_admin_failure() throws Exception{
        // given

        final String tokenUser = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IlNUQUZGIiwibmFtZSI6bnVsbCwiZXhwIjoxNjg1NDI1MDU4LCJ1c2VybmFtZSI6InVzZXIifQ.ztgu4tqLt6xJFhl4tfT-RA7bnW8F5tmYycW6HVcZWCe7R_8-1JxPDxUh2rowIEdmNXuj3iRPjvb0LbtH38a7Mw";


        RoleChangeRequest request = RoleChangeRequest.builder()
                .username("user")
                .role(Role.ADMIN)
                .build();

        // when
        ResultActions resultActions = mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/member/admin/role/modify")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",tokenUser)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().is(403));
        // then
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}
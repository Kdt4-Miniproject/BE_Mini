package org.vacation.back.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.RestController;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.*;

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
    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjg1NDI2MzUzLCJ1c2VybmFtZSI6ImFkbWluIn0.c64_UFFi4mcrFi5P4f9hmghou26kL6_kMP6Xo12ZMmy6c7gdobhccrKpN0_M4cEh47smZBanMq_5uJwIRxpEMg";

    @Test
    @DisplayName("/api/v1/join")
    void member_register() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.UNAUTHORIZED).build();

        RegisterMemberDTO dto = RegisterMemberDTO.builder()
                .username("admin")
                .password("1234")
                .birthDate("2023-04-26")
                .name("김독자")
                .department("개발")
                .position("대리")
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

        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);

    }


    @Test
    @DisplayName("/api/v1/member/modify")
    void member_dto_modify() throws Exception {
        // given
        MemberModifyDTO dto  = MemberModifyDTO.builder()
                .phoneNumber("010-1234-1234")
                .employeeNumber("20220")
                .email("test@naver.com")
                .year("4")
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
                .password("1234")
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
     * @apiNote 관리자 권한으로는 해당 멤버에 모든걸 변경할 수 있디
     * */
    @Test
    @DisplayName("")
    void admin_member_modify_member() throws Exception {
        // given
        AdminMemberModifyRequest request = AdminMemberModifyRequest.builder()
                .username("admin")
                .password("1234")
                .birthdate("2023-04-26")
                .phoneNumber("010-1234-1234")
                .name("김독자")
                .department("개발")
                .position("대리")
                .years("3")
                .role(Role.STAFF)
                .email("test@naver.com")
                .deleted(false)
                .build();
        // when
        ResultActions resultActions = mockMvc
                .perform(RestDocumentationRequestBuilders.post("/api/v1/member/admin/modify")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization",token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk());
        // then
        resultActions.andExpect(jsonPath("$.status").value(200));

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
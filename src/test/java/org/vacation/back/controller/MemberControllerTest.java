package org.vacation.back.controller;

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
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.LoginRequestDTO;
import org.vacation.back.dto.request.member.MemberModifyDTO;
import org.vacation.back.dto.request.member.RegisterMemberDTO;

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
    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwiZXhwIjoxNjgyODI5OTMxLCJ1c2VybmFtZSI6ImFkbWluIn0.xYlExakeRHLrNErB8j6PrpbIQgBWxwX4BsAC2OxHmABOELoBN4f4LBvshjZEAAyQyNVTy8aX66uipWnwCucQuQ";


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
        resultActions.andExpect(header().exists("X-Refresh-Token"));

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





}
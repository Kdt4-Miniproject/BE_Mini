package org.vacation.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.VacationTemp;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.VacationTempDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationOkAndRejectedDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("연차 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VacationControllerTest extends MyWithRTestDoc{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjgzMDAxMjIyLCJ1c2VybmFtZSI6ImFkbWluIn0.j_MHfFe1BC-mGEshWZeg0ynyO9OfUfPFBT8P_Nt-BPo9qZe4jMwoZeDkDO2HeiXxN2yRTVvC6WUwxYkA6UfN1A";
    final String RefreshToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjg1NTA2ODIyLCJ1c2VybmFtZSI6ImFkbWluIn0.IUsPdcR6VUe4lX1f10W7vCx74Siw2Q85Yz6tFuyqf9-8_un0J4n0Ut8U7KP44x1F-lOxttp1emAS5i9JhIOamw";

    @Test
    @DisplayName("/api/v1/vacation/save")
    void vacation_save() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();


        VacationSaveRequestDTO dto = VacationSaveRequestDTO.builder()
                .username("admin")
                .start("2023-05-01")
                .end("2023-05-01")
                .deleted(false)
                .status(VacationStatus.WAITING)
                .build();

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/save")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/vacation/detail/{id}")
    void vacation_detail() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/vacation/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/vacation/list")
    void vacation_list() throws Exception {
        // given

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/vacation/list")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


    @Test
    @DisplayName("/api/v1/vacation/modify/{id}")
    void vacation_modify() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;

        VacationModifyDTO dto = VacationModifyDTO.builder()
                .start("2023-05-01")
                .end("2023-05-01")
                .build();

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/vacation/delete/{id}")
    void vacation_delete() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/vacation/ok/{id}")
    void vacation_ok() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;


        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/ok/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/vacation/rejected/{id}")
    void vacation_rejected() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;


        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/rejected/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
                .header("X-Refresh-Token", RefreshToken)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

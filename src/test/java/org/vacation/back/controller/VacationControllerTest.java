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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.service.MemberService;
import org.vacation.back.service.VacationService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

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

    @Autowired
    private VacationService vacationService;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjgzNzE0Njg4LCJ1c2VybmFtZSI6ImFkbWluIn0.o1cHAhkwvDoWQ2Gnl-VY9WBn_TOu4RahiUrhLFlP7-jGfb_p-YVH3EY1xQ8wl7Xg68BSpi3UTh2RMjq1uxN_Vw";


    @BeforeEach
    void vacation_saveBefore() {
        RegisterMemberDTO dto = RegisterMemberDTO.builder()
                .username("admin")
                .password("1234")
                .birthDate("2023-04-26")
                .phoneNumber("010-1234-1234")
                .positionName("사원")
                .departmentName("개발")
                .fileName("404.jpg")
                .name("김독자")
                .joiningDay("2020-01-01")
                .years("3")
                .email("test@naver.com")
                .build();



        VacationSaveRequestDTO save_test1 = VacationSaveRequestDTO.builder()
                .memberUsername("admin")
                .start(LocalDate.parse("2023-05-01"))
                .end(LocalDate.parse("2023-05-01"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test1, dto.getUsername());

        VacationSaveRequestDTO save_test2 = VacationSaveRequestDTO.builder()
                .memberUsername("admin")
                .start(LocalDate.parse("2023-05-02"))
                .end(LocalDate.parse("2023-05-02"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test2, dto.getUsername());

        VacationSaveRequestDTO save_test3 = VacationSaveRequestDTO.builder()
                .memberUsername("admin")
                .start(LocalDate.parse("2023-06-01"))
                .end(LocalDate.parse("2023-06-02"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test3, dto.getUsername());
    }
    @Test
    @DisplayName("/api/v1/vacation/save")
    void vacation_save() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();


        VacationSaveRequestDTO dto = VacationSaveRequestDTO.builder()
                .memberUsername("admin")
                .start(LocalDate.parse("2023-05-09"))
                .end(LocalDate.parse("2023-05-10"))
                .status(VacationStatus.WAITING)
                .build();

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/save")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization",token)
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
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/vacation/list/{month}")
    void vacation_list() throws Exception {
        // given
        String month = "";
        //when
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/vacation/list/{month}", month)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
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
                .start(LocalDate.parse("2023-05-02"))
                .end(LocalDate.parse("2023-05-07"))
                .build();

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/vacation/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto))
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then


        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.start").value("2023-05-02"));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.end").value("2023-05-07"));
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
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.status").value("DELETED"));
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
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.status").value("OK"));
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
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.status").value("REJECTED"));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.*;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.dto.request.vacation.VacationModifyDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IiIsInJvbGUiOiJTVEFGRiIsIm5hbWUiOiLsnKDsoIAiLCJleHAiOjE2ODM4NTY4ODIsInVzZXJuYW1lIjoidXNlciJ9.HNkoqtz6gX0HMXV3tIajQRKDWjrtQwKFBabkyEK9ivvhohLDphRczYRxW0yt4_R6fphMlESOsTK49cX7RfETUA";


    @BeforeEach
    void vacation_saveBefore() {
        departmentRepository.save(Department.builder()
                .departmentName("개발")
                        .vacationLimit(2)
                .departmentPersonal(10)
                .build());
        Department department = departmentRepository.save(Department.builder()
                .departmentName("인사")
                .vacationLimit(2)
                .departmentPersonal(10)
                .build());
        departmentRepository.save(Department.builder()
                .departmentName("마케팅")
                .vacationLimit(2)
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

        Member member = memberRepository.findById("user").orElseThrow();
        //given2
        String username = "user";

        VacationSaveRequestDTO save_test1 = VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-05-01"))
                .end(LocalDate.parse("2023-05-01"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test1);

        VacationSaveRequestDTO save_test2 = VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-05-02"))
                .end(LocalDate.parse("2023-05-02"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test2);

        VacationSaveRequestDTO save_test3 = VacationSaveRequestDTO.builder()
                .memberUsername("user")
                .start(LocalDate.parse("2023-06-01"))
                .end(LocalDate.parse("2023-06-02"))
                .status(VacationStatus.WAITING)
                .build();
        vacationService.vacationSave(save_test3);
    }


    @Test
    @DisplayName("/api/v1/vacation/save")
    void vacation_save() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();


        VacationSaveRequestDTO dto = VacationSaveRequestDTO.builder()
                .memberUsername("user")
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
        String month = "0";
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

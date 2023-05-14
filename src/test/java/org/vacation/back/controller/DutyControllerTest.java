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
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.*;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.duty.DutyAssignDTO;
import org.vacation.back.dto.request.duty.DutyModifyDTO;
import org.vacation.back.dto.request.duty.DutySaveRequestDTO;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.dto.request.vacation.VacationSaveRequestDTO;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.service.DutyService;
import org.vacation.back.service.VacationService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("당직 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DutyControllerTest extends MyWithRTestDoc {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DutyRepository dutyRepository;

    @Autowired
    private DutyService dutyService;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IjQwNC5qcGciLCJyb2xlIjoiTEVBREVSIiwibmFtZSI6Iuq5gOuPheyekCIsInBvc2l0aW9uIjoi6rO87J6lIiwiZXhwIjoxNjg2NjI1MzAwLCJkZXBhcnRtZW50Ijoi6rCc67CcIiwiaWF0IjoxNjg0MDMzMzAwLCJ1c2VybmFtZSI6InVzZXIxIn0.y90D3Z86p1pwZHrtJ5geI-i9nZ0m8lysXWgipVnz28b8CmfFEWgtF_4dr3LgsuTrpY6poOZJ-kPrqESK-ahb_A";
    final String RefreshToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjg2MzcxNjI3LCJ1c2VybmFtZSI6ImFkbWluIn0.Ne0sxMJqJULkQrhrK8mVO2aTUAJreBfC8xbGbA9cs_IFG9buCTNjXkuyAVKj7-WF8jaQmf9O7_P9eYIe8NZ_RQ";


    @BeforeEach
    void duty_saveBefore() {

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

        DutySaveRequestDTO save_test1 = DutySaveRequestDTO.builder()
                .username("user")
                .day(LocalDate.parse("2023-05-12"))
                .status(DutyStatus.WAITING)
                .build();
        dutyService.dutySave(save_test1);

        DutySaveRequestDTO save_test2 = DutySaveRequestDTO.builder()
                .username("user")
                .day(LocalDate.parse("2023-05-13"))
                .status(DutyStatus.WAITING)
                .build();
        dutyService.dutySave(save_test2);

        DutySaveRequestDTO save_test3 = DutySaveRequestDTO.builder()
                .username("user")
                .day(LocalDate.parse("2023-05-14"))
                .status(DutyStatus.WAITING)
                .build();
        dutyService.dutySave(save_test3);
    }


    @Test
    @DisplayName("/api/v1/duty/save")
    void duty_save() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();

        DutySaveRequestDTO dutySaveRequestDTO = DutySaveRequestDTO.builder()
                .username("user")
                .day(LocalDate.parse("2023-05-13"))
                .status(DutyStatus.WAITING)
                .build();



        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/duty/save")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dutySaveRequestDTO))
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/duty/detail/{id}")
    void duty_detail() throws Exception {
        // given
        Long id = 1L;

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/duty/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/duty/list/{month}")
    void duty_list() throws Exception {
        // given
        String month = "0";
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/duty/list/{month}", month)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }



    @Test
    @DisplayName("/api/v1/duty/modify/{id}")
    void duty_modify() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;

        DutyModifyDTO dutyModifyDTO = DutyModifyDTO.builder()
                .day(LocalDate.parse("2023-05-15"))
                .build();

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/duty/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dutyModifyDTO))
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.day").value("2023-05-15"));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/duty/delete/{id}")
    void duty_delete() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/duty/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.status").value("DELETED"));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/duty/ok/{id}")
    void duty_ok() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;


        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/duty/ok/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.status").value("OK"));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/duty/rejected/{id}")
    void duty_rejected() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        Long id = 1L;


        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/duty/rejected/{id}", id)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data.status").value("REJECTED"));
        resultActions.andExpect(jsonPath("$.status").value(200));
        resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/duty/assign")
    void duty_assign() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();
        String username = "admin";

        DutyAssignDTO dutyAssignDTO = DutyAssignDTO.builder()
                .memberUsername(username)
                .build();

        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/duty/assign")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization",token)
        ).andExpect(status().isOk());


        //then
        resultActions.andExpect(jsonPath("$.data").value(true));
        resultActions.andExpect(jsonPath("$.status").value(200));
       resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}

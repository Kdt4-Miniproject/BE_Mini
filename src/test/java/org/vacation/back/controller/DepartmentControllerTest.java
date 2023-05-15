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
import org.vacation.back.domain.Department;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;
import org.vacation.back.repository.DepartmentRepository;

import javax.persistence.EntityManager;

@DisplayName("Department API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DepartmentControllerTest extends MyWithRTestDoc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EntityManager em;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IiIsInJvbGUiOiJBRE1JTiIsIm5hbWUiOiLqtIDrpqzsnpAiLCJwb3NpdGlvbiI6IuuMgOumrCIsImV4cCI6MTY4NDEzNDU0OSwiZGVwYXJ0bWVudCI6IuyduOyCrCIsImlhdCI6MTY4NDA0ODE0OSwidXNlcm5hbWUiOiJhZG1pbiJ9.eXkBRc9Kxiv3WDx5vWntpd238qu8Ik5GA_Wcgj9p09BwbVVeqyltUioSmzRRZj3Fp7ZGTAILxGXS2mXiVIKazA";

    @BeforeEach
    public void setUp() {
        departmentRepository.save(Department.builder()
                .departmentName("인사")
                .vacationLimit(3)
                .departmentPersonal(11)
                .build());
        departmentRepository.save(Department.builder()
                .departmentName("개발")
                .vacationLimit(5)
                .departmentPersonal(10)
                .build());
        em.clear();
    }

    @Test
    @DisplayName("/api/v1/department/save-성공")
    public void department_save() throws Exception {
        // given
        DepartmentSaveDTO departmentDTO = DepartmentSaveDTO.builder()
                .departmentName("영업")
                .vacationLimit("3")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/save-실패-departmentName 빈칸, 범위미만초과, 자음모음")
    public void department_save_failed() throws Exception {
        // given
        DepartmentSaveDTO departmentDTO = DepartmentSaveDTO.builder()
                .departmentName("영")
                .vacationLimit("3")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/save-실패-departmentName 값이 DB에 이미 있을때")
    public void department_save_exist_failed() throws Exception {
        // given
        DepartmentSaveDTO departmentDTO = DepartmentSaveDTO.builder()
                .departmentName("인사")
                .vacationLimit("3")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("이미 존재하는 부서입니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/save-실패-vacationLimit 빈칸, 범위초과, 문자열")
    public void department_save_limit_failed() throws Exception {
        // given
        DepartmentSaveDTO departmentDTO = DepartmentSaveDTO.builder()
                .departmentName("마케팅")
                .vacationLimit("9일1")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서별 휴가제한인원은 1~2자리 숫자만 입력해 주세요."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/detail-성공")
    public void department_detail() throws Exception {
        // given
        String id = "개발";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/department/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );
        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.departmentName").value("개발"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/detail-실패-departmentName 범위미만초과, 자음모음")
    public void department_detail_failed() throws Exception {
        // given
        String id = "개";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/department/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );
        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/detail-실패-departmentName 범위 내에서 찾는 값이 없을 때")
    public void department_detail_not_found_failed() throws Exception {
        // given
        String id = "개발팀";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/department/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );
        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서를 찾을 수 없습니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/list-성공")
    public void department_find_all() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/department/list")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/modify-성공")
    public void department_modify() throws Exception {
        // given
        String id = "인사";
        DepartmentModifyDTO departmentDTO = DepartmentModifyDTO.builder()
                .vacationLimit("99")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then

        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.departmentName").value("인사"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.vacationLimit").value("99"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));


        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/modify-실패-departmentName 빈칸, 범위미만초과, 자음모음")
    public void department_modify_failed() throws Exception {
        // given
        String id = "인";
        DepartmentModifyDTO departmentDTO = DepartmentModifyDTO.builder()
                .vacationLimit("99")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/modify-실패-vacationLimit 빈칸, 범위초과, 문자열")
    public void department_modify_limit_failed() throws Exception {
        // given
        String id = "인사";
        DepartmentModifyDTO departmentDTO = DepartmentModifyDTO.builder()
                .vacationLimit("9gg9")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(departmentDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서별 휴가제한인원은 1~2자리 숫자만 입력해 주세요."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/delete-성공")
    public void department_delete() throws Exception {
        // given
        String id = "인사";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/delete-실패-departmentName 범위미만초과, 자음모음")
    public void department_delete_failed() throws Exception {
        // given
        String id = "인ㅇ사";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/department/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("부서명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

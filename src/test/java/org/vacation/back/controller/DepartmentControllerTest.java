package org.vacation.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Department;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Position;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;

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
    private MemberRepository memberRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager em;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IjQwNC5qcGciLCJyb2xlIjoiTEVBREVSIiwibmFtZSI6Iuq5gOuPheyekCIsInBvc2l0aW9uIjoi6rO87J6lIiwiZXhwIjoxNjg2NjI1MzAwLCJkZXBhcnRtZW50Ijoi6rCc67CcIiwiaWF0IjoxNjg0MDMzMzAwLCJ1c2VybmFtZSI6InVzZXIxIn0.y90D3Z86p1pwZHrtJ5geI-i9nZ0m8lysXWgipVnz28b8CmfFEWgtF_4dr3LgsuTrpY6poOZJ-kPrqESK-ahb_A";

    @BeforeEach
    public void setUp() {
        DepartmentSaveDTO dto = new DepartmentSaveDTO();
        dto.setDepartmentName("인사");
        dto.setVacationLimit(3);
        dto.setDepartmentPersonal(6);
        departmentRepository.save(dto.toEntity());
        em.clear();
    }

    @Test
    @DisplayName("/api/v1/department/save")
    public void department_save() throws Exception {
        // given
        DepartmentSaveDTO departmentDTO = DepartmentSaveDTO.builder()
                .departmentName("영업")
                .vacationLimit(3)
                .departmentPersonal(6)
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/department/save")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO))
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/detail")
    public void department_detail() throws Exception {
        // given
        String id = "개발";

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/department/detail/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.departmentName").value("개발"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/list")
    public void department_find_all() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/v1/department/list")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/modify")
    public void department_modify() throws Exception {
        // given
        String id = "인사";
        DepartmentModifyDTO departmentDTO = DepartmentModifyDTO.builder()
                .vacationLimit(4)
                .departmentPersonal(10)
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/department/modify/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO))
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then

        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/department/delete")
    public void department_delete() throws Exception {
        // given

        String id = "인사";

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/department/delete/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


}

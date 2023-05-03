package org.vacation.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.annotation.Permission;
import org.vacation.back.common.DepartmentStatus;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.dto.request.Position.PositionDeleteDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;
import org.vacation.back.dto.request.department.DepartmentDeleteDTO;
import org.vacation.back.dto.request.department.DepartmentModifyDTO;
import org.vacation.back.dto.request.department.DepartmentSaveDTO;

@DisplayName("부서 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DepartmentControllerTest extends MyWithRTestDoc {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjgzMTg0Mjg4LCJ1c2VybmFtZSI6ImFkbWluIn0.J3_AV5w2DMKawdu6eLhowA4duZc-aWR7EyoRUvh-bFx9VMi3KWwh7K9DmRaO9TP4QarvRNUlrHCb3jSD_YSvUw";

    @Permission
    @DisplayName("/api/v1/department/save")
    @Test
    public void department_save() throws Exception {
        // given

        DepartmentSaveDTO departmentDTO = DepartmentSaveDTO.builder()
                .department(DepartmentStatus.HUMAN_RESOURCE)
                .vacation_limit("3")
                .personal("6")
                .deleted(false)
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/department/save")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO))
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("/api/v1/department/detail")
    @Test
    public void department_detail() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/department/detail")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Permission
    @DisplayName("/api/v1/department/list")
    @Test
    public void department_find_all() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/v1/department/list")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Permission
    @DisplayName("/api/v1/department/modify")
    @Test
    public void department_modify() throws Exception {
        // given

        DepartmentModifyDTO departmentDTO = DepartmentModifyDTO.builder()
                .department(DepartmentStatus.DEVELOPMENT)
                .vacation_limit("4")
                .personal("10")
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/department/modify")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO))
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("/api/v1/department/delete")
    @Test
    public void department_delete() throws Exception {
        // given

        DepartmentDeleteDTO departmentDTO = DepartmentDeleteDTO.builder()
                .department(DepartmentStatus.MARKETING)
                .deleted(true)
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/department/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(departmentDTO))
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }


}

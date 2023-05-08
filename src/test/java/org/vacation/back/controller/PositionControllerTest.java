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
import org.vacation.back.common.PositionStatus;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.request.Position.PositionDeleteDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("직급 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PositionControllerTest extends MyWithRTestDoc {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjgzMjAxMTkwLCJ1c2VybmFtZSI6ImFkbWluIn0.zzA6T2uisyHiyo8v-iaqCzcuHQ_mepDZe5ExP_9DPlKIKl6J11s-Nxu4vOI6FAwnK3PiVMgGZHzelA4oNTi_gg";
    final String RefreshToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6bnVsbCwicm9sZSI6IkFETUlOIiwibmFtZSI6bnVsbCwiZXhwIjoxNjg1NTA2ODIyLCJ1c2VybmFtZSI6ImFkbWluIn0.IUsPdcR6VUe4lX1f10W7vCx74Siw2Q85Yz6tFuyqf9-8_un0J4n0Ut8U7KP44x1F-lOxttp1emAS5i9JhIOamw";

    @Permission
    @DisplayName("/api/v1/position/save")
    @Test
    public void position_save() throws Exception {
        // given
//        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();

        PositionSaveDTO positionDTO = PositionSaveDTO.builder()
                .position(PositionStatus.STAFF)
                .vacation("1")
                .deleted(false)
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position/save")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO))
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @DisplayName("/api/v1/position/detail")
    @Test
    public void position_detail() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/position/detail")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Permission
    @DisplayName("/api/v1/position/list")
    @Test
    public void position_find_all() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/v1/position/list")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Permission
    @DisplayName("/api/v1/position/modify")
    @Test
    public void position_modify() throws Exception {
        // given

        PositionModifyDTO positionDTO = PositionModifyDTO.builder()
                .position(PositionStatus.ASSISTANT_MANAGER)
                .vacation("5")
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position/modify")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO))
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Permission
    @DisplayName("/api/v1/position/delete")
    @Test
    public void position_delete() throws Exception {
        // given

        PositionDeleteDTO positionDTO = PositionDeleteDTO.builder()
                .position(PositionStatus.DEPARTMENT_MANAGER)
                .deleted(true)
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO))
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

}

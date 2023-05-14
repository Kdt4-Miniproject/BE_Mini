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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vacation.back.MyWithRTestDoc;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;
import org.vacation.back.repository.PositionRepository;

import javax.persistence.EntityManager;

@DisplayName("Position API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PositionControllerTest extends MyWithRTestDoc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EntityManager em;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IjQwNC5qcGciLCJyb2xlIjoiTEVBREVSIiwibmFtZSI6Iuq5gOuPheyekCIsInBvc2l0aW9uIjoi6rO87J6lIiwiZXhwIjoxNjg2NjI1MzAwLCJkZXBhcnRtZW50Ijoi6rCc67CcIiwiaWF0IjoxNjg0MDMzMzAwLCJ1c2VybmFtZSI6InVzZXIxIn0.y90D3Z86p1pwZHrtJ5geI-i9nZ0m8lysXWgipVnz28b8CmfFEWgtF_4dr3LgsuTrpY6poOZJ-kPrqESK-ahb_A";

    @BeforeEach
    public void setUp() {
        PositionSaveDTO dto = new PositionSaveDTO();
        dto.setPositionName("과장");
        dto.setVacation("3");
        positionRepository.save(dto.toEntity());
        em.clear();
    }

    @Test
    @DisplayName("/api/v1/position/save")
    public void position_save() throws Exception {
        // given
        PositionSaveDTO positionDTO = PositionSaveDTO.builder()
                .positionName("대리")
                .vacation("1")
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position/save")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO))
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/detail")
    public void position_detail() throws Exception {
        // given
        String id = "과장";

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/position/detail/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/list")
    public void position_find_all() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                        .get("/api/v1/position/list")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/modify")
    public void position_modify() throws Exception {
        // given
        String id = "과장";
        PositionModifyDTO positionDTO = PositionModifyDTO.builder()
                .vacation("99")
                .build();

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position/modify/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO))
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/delete")
    public void position_delete() throws Exception {
        // given
        String id = "과장";

        // when
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/position/delete/{id}", id)
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

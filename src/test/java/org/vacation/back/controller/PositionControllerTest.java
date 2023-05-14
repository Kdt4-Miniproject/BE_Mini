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
import org.vacation.back.domain.Position;
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

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IiIsInJvbGUiOiJBRE1JTiIsIm5hbWUiOiLqtIDrpqzsnpAiLCJwb3NpdGlvbiI6IuuMgOumrCIsImV4cCI6MTY4NDEzNDU0OSwiZGVwYXJ0bWVudCI6IuyduOyCrCIsImlhdCI6MTY4NDA0ODE0OSwidXNlcm5hbWUiOiJhZG1pbiJ9.eXkBRc9Kxiv3WDx5vWntpd238qu8Ik5GA_Wcgj9p09BwbVVeqyltUioSmzRRZj3Fp7ZGTAILxGXS2mXiVIKazA";

    @BeforeEach
    public void setUp() {
        positionRepository.save(Position.builder()
                .positionName("사원")
                .vacation("2")
                .build());
        positionRepository.save(Position.builder()
                .positionName("대리")
                .vacation("4")
                .build());
        em.clear();
    }

    @Test
    @DisplayName("/api/v1/position/save-성공")
    public void position_save() throws Exception {
        // given
        PositionSaveDTO positionDTO = PositionSaveDTO.builder()
                .positionName("과장")
                .vacation("6")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/save-실패-positionName 빈칸, 범위미만초과, 자음모음")
    public void position_save_failed() throws Exception {
        // given
        PositionSaveDTO positionDTO = PositionSaveDTO.builder()
                .positionName("과ㄹ장")
                .vacation("6")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/save-실패-positionName 값이 DB에 이미 있을때")
    public void position_save_exist_failed() throws Exception {
        // given
        PositionSaveDTO positionDTO = PositionSaveDTO.builder()
                .positionName("사원")
                .vacation("6")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("이미 존재하는 직급입니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/save-실패-vacation 빈칸, 범위초과, 문자열")
    public void position_save_vacation_failed() throws Exception {
        // given
        PositionSaveDTO positionDTO = PositionSaveDTO.builder()
                .positionName("과장")
                .vacation("6일일")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급별 연차 일수는 1~2자리 숫자만 입력해 주세요."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/detail-성공")
    public void position_detail() throws Exception {
        // given
        String id = "대리";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/position/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/detail-실패-positionName 빈칸, 범위미만초과, 자음모음")
    public void position_detail_failed() throws Exception {
        // given
        String id = "대ㅂ리";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/position/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/detail-실패-positionName 범위 내에서 찾는 값이 없을 때")
    public void position_detail_not_found_failed() throws Exception {
        // given
        String id = "사장";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/position/detail/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급를 찾을 수 없습니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/list-성공")
    public void position_find_all() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/api/v1/position/list")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/modify-성공")
    public void position_modify() throws Exception {
        // given
        String id = "대리";
        PositionModifyDTO positionDTO = PositionModifyDTO.builder()
                .vacation("99")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isOk());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.positionName").value("대리"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data.vacation").value("99"));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));


        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/modify-실패-positionName 빈칸, 범위미만초과, 자음모음")
    public void position_modify_failed() throws Exception {
        // given
        String id = "리";
        PositionModifyDTO positionDTO = PositionModifyDTO.builder()
                .vacation("29")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));


        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/modify-실패-vacation 빈칸, 범위초과, 문자열")
    public void position_modify_vacation_failed() throws Exception {
        // given
        String id = "대리";
        PositionModifyDTO positionDTO = PositionModifyDTO.builder()
                .vacation("2ggg9")
                .build();

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/modify/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(positionDTO))
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급별 연차 일수는 1~2자리 숫자만 입력해 주세요."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }

    @Test
    @DisplayName("/api/v1/position/delete-성공")
    public void position_delete() throws Exception {
        // given
        String id = "사원";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/delete/{id}", id)
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
    @DisplayName("/api/v1/position/delete-실패-positionName 범위미만초과, 자음모음")
    public void position_delete_failed() throws Exception {
        // given
        String id = "사ㅇ";

        // when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/api/v1/position/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
        );

        // then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(false));
        actions.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("직급명은 2~10자 이내로 한글/영어/숫자만 입력 가능합니다."));

        actions.andDo(MockMvcResultHandlers.print()).andDo(document);
    }
}

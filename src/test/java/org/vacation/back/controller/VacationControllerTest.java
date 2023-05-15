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
import org.vacation.back.repository.VacationRepository;
import org.vacation.back.service.MemberService;
import org.vacation.back.service.VacationService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

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
    private VacationRepository vacationRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKV1QiLCJpbWFnZSI6IjQwNC5qcGciLCJyb2xlIjoiTEVBREVSIiwibmFtZSI6Iuq5gOuPheyekCIsInBvc2l0aW9uIjoi6rO87J6lIiwiZXhwIjoxNjg2NjI1MzAwLCJkZXBhcnRtZW50Ijoi6rCc67CcIiwiaWF0IjoxNjg0MDMzMzAwLCJ1c2VybmFtZSI6InVzZXIxIn0.y90D3Z86p1pwZHrtJ5geI-i9nZ0m8lysXWgipVnz28b8CmfFEWgtF_4dr3LgsuTrpY6poOZJ-kPrqESK-ahb_A";


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

//        VacationSaveRequestDTO save_test1 = VacationSaveRequestDTO.builder()
//                .start(LocalDate.parse("2023-05-01"))
//                .end(LocalDate.parse("2023-05-01"))
//                .build();
//        vacationService.vacationSave(save_test1);
//
//        VacationSaveRequestDTO save_test2 = VacationSaveRequestDTO.builder()
//                .start(LocalDate.parse("2023-05-02"))
//                .end(LocalDate.parse("2023-05-02"))
//                .build();
//        vacationService.vacationSave(save_test2);
//
//        VacationSaveRequestDTO save_test3 = VacationSaveRequestDTO.builder()
//                .start(LocalDate.parse("2023-06-01"))
//                .end(LocalDate.parse("2023-06-02"))
//                .status(VacationStatus.WAITING)
//                .build();
//        vacationService.vacationSave(save_test3);
    }


    @Test
    @DisplayName("/api/v1/vacation/save")
    void vacation_save() throws Exception {
        // given
        CommonResponse.builder().data("String").codeEnum(CodeEnum.SUCCESS).build();


        VacationSaveRequestDTO dto = VacationSaveRequestDTO.builder()
                .start(LocalDate.parse("2023-05-09"))
                .end(LocalDate.parse("2023-05-10"))
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
    void test123(){
        vacationRepository.save(Vacation.builder()
                        .member(memberRepository.findById("user").get())
                        .start(LocalDate.parse("2023-05-01"))
                        .end(LocalDate.parse("2023-05-01"))
                        .status(VacationStatus.OK)
                .build());

        vacationRepository.save(Vacation.builder()
                .member(memberRepository.findById("admin").get())
                .start(LocalDate.parse("2023-05-01"))
                .end(LocalDate.parse("2023-05-01"))
                .status(VacationStatus.OK)
                .build());

        System.out.println("+++++++++여기서 에러나야함 +++++++++++++");

        HttpServletRequest request = new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String name) {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String name) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean create) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public String changeSessionId() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String username, String password) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String name) throws IOException, ServletException {
                return null;
            }

            @Override
            public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return "test";
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public long getContentLengthLong() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String name) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String name, Object o) {

            }

            @Override
            public void removeAttribute(String name) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String path) {
                return null;
            }

            @Override
            public String getRealPath(String path) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }
        };

        vacationService.vacationSave(VacationSaveRequestDTO.builder()
                .start(LocalDate.parse("2023-05-01"))
                .end(LocalDate.parse("2023-05-01"))
                .build(), request);
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

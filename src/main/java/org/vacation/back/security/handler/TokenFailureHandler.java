package org.vacation.back.security.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class TokenFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Gson gson = new Gson();

        log.error("USERANEM OF PASSWORD NOT VALID");

        CommonResponse<?> commonResponse = CommonResponse.builder()
                .codeEnum(CodeEnum.UNAUTHORIZED)
                .data(false)
                .build();

        response.setStatus(CodeEnum.UNAUTHORIZED.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().println(gson.toJson(commonResponse));
    }
}

package org.vacation.back.security.handler;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.security.JwtAuthenticationToken;
import org.vacation.back.utils.JWTUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;

        MemberDTO memberDTO = token.getPrincipal();

        String accessToken = jwtUtils.create(memberDTO,false);
        String refreshToken = jwtUtils.create(memberDTO,true);

        response.setHeader("Authorization",accessToken);
        response.setHeader("X-Refresh-Token",refreshToken);

        Gson gson = new Gson();
        CommonResponse commonResponse = CommonResponse.builder()
                .codeEnum(CodeEnum.SUCCESS)
                .data(true)
                .build();


        response.getWriter().println(gson.toJson(commonResponse));
    }
}

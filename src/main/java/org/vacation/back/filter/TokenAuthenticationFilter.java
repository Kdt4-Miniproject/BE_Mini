package org.vacation.back.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.vacation.back.security.JwtAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;


@Slf4j
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    public TokenAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if ( !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        log.info("LOGIN FILTER RUNNNING");
        Map<String,String> jsonData = parseRequestJSON(request);

        String username = jsonData.get("username");
        username = (username != null) ? username.trim() : "";
        String password = jsonData.get("password");
        password = (password != null) ? password : "";
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username,password);

        log.debug("FILTER에 들어온 인증 토큰 ->> {}",authRequest);
        // Allow subclasses to set the "details" property
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        //JSON 데이터를 분석해서 mid. mpw 전달 값을 Map으로 처리
        try(Reader render = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();

            return gson.fromJson(render,Map.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

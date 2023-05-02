package org.vacation.back.filter;


import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vacation.back.exception.token.TokenException;
import org.vacation.back.utils.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AccessCheckFilter extends OncePerRequestFilter
{


    private final JWTUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if(path.startsWith("/api/v1/login") || path.startsWith("/api/v1/join") || path.equalsIgnoreCase("/api/v1/join/check/**")){
            filterChain.doFilter(request,response);
            return;
        }


        log.warn(path);
        try{
            Map<String, Claim> token = validateAccessToken(request);

            String role = token.get("role").asString();
            String username = token.get("username").asString();

            request.setAttribute("role",role);
            request.setAttribute("username",username);

            filterChain.doFilter(request,response);

        }catch (TokenException tokenExpiredException){
            tokenExpiredException.sendResponseError(response);
        }
    }


    public Map<String, Claim> validateAccessToken(HttpServletRequest request) throws TokenException {
        String headerStr = request.getHeader("Authorization");

        if(headerStr == null || headerStr.length()<8){
            throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
        }

        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        try{
            DecodedJWT decodedJWT = jwtUtils.verify(tokenStr);
            Map<String, Claim> claimMap = decodedJWT.getClaims();
            log.info("TOKEN 통과");
            return claimMap;
        }catch (TokenExpiredException tokenExpiredException){//시간만료
            log.warn("TOKEN EXPIRED - Refresh Token request Is required 403 ");
            throw  new TokenException(TokenException.TOKEN_ERROR.EXPIRED);
        }catch (SignatureVerificationException signatureVerificationException){ //토큰의 서명 검증이 실패한 경우
            log.warn("TOKEN BADSIGN - Token has changed its signature changed ");
            throw  new TokenException(TokenException.TOKEN_ERROR.BADSIGN);
        }catch (InvalidClaimException invalidClaimException){ //토큰의 클레임 검증 실패 클레임이 없거나 예상값과 다를 때
            log.warn("TOKEN INVALID - Invalid token.");
            throw  new TokenException(TokenException.TOKEN_ERROR.MALFORM);
        }catch (JWTDecodeException jwtDecodeException){ //토큰손상되면 또는 올바른 형식이 아니.면
            log.warn("TOKEN MALFORM - Invalid token ");
            throw  new TokenException(TokenException.TOKEN_ERROR.MALFORM);
        }
    }

}

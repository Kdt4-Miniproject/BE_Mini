package org.vacation.back.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vacation.back.domain.Role;
import org.vacation.back.dto.CodeEnum;
import org.vacation.back.dto.CommonResponse;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.exception.token.TokenException;
import org.vacation.back.utils.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RefreshCheckFilter extends OncePerRequestFilter {

    private final String refreshPath;
    private final JWTUtils jwtUtils;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        if (!refreshPath.equals(request.getRequestURI())) {
            log.info("skip refresh token filter.....");
            filterChain.doFilter(request, response);
            return;
        }

        if(!request.getMethod().equalsIgnoreCase("POST")){
            log.info("skip refresh token filter.....");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("=== REFRESH FILTER ===");
        String refreshToken = request.getHeader("X-Auth-Refresh-Token").substring(7);

      try{
          DecodedJWT decodedRefresh = jwtUtils.verify(refreshToken);

          String refreshUsername = decodedRefresh.getClaim("username").asString();
          Role refreshRole = decodedRefresh.getClaim("role").as(Role.class);
          String refreshImage = decodedRefresh.getClaim("image").asString();
          String refreshName = decodedRefresh.getClaim("name").asString();

          Integer exp = decodedRefresh.getClaim("exp").asInt();


          Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);

          Date current = new Date(System.currentTimeMillis());

          long gapTime = (expTime.getTime() - current.getTime());

          MemberDTO dto = MemberDTO.builder()
                  .username(refreshUsername)
                  .name(refreshName)
                  .fileName(refreshImage)
                  .role(refreshRole)
                  .build();


          String accessToken = jwtUtils.create(dto,false);

          if(gapTime < (1000 * 60 * 60 * 24 * 3)){ //3일
              log.info("new RefreshToken required...");
              refreshToken = jwtUtils.create(dto,true);
          }

          sendTokens(accessToken,refreshToken,response);
      }catch (TokenExpiredException tokenExpiredException){
          sendError(response, TokenException.TOKEN_ERROR.EXPIRED);
      }catch (SignatureVerificationException signatureVerificationException){
          sendError(response, TokenException.TOKEN_ERROR.BADSIGN);
      }

    }
    private void sendTokens(String accesTokenValue,String refreshTokenValue, HttpServletResponse response){

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Authorization",accesTokenValue);
        response.setHeader("X-Refresh-Token","Bearer refreshTokenValue");

        Gson gson = new Gson();

        String jsonStr = gson.toJson(CommonResponse.builder().codeEnum(CodeEnum.SUCCESS).data(true).build());

        try{
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendError(HttpServletResponse response, TokenException.TOKEN_ERROR tokenError){
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(tokenError.getStatus());
        Gson gson = new Gson();


        String jsonStr = gson.toJson(new CommonResponse<>().data(false).tokenError(tokenError));
        try{
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

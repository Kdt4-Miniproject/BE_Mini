package org.vacation.back.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.vacation.back.dto.common.MemberDTO;

import java.util.Date;


@Component
public class JWTUtils {
    private static final String SUBJECT = "JWT";
    private static final int EXP = 1000 * 60 * 60 * 24;
    private static final long REFRESH_EXP = 1000L * 60 * 60 * 24 * 30;
    public static final String TOKEN_PREFIX = "Bearer "; // 스페이스 필요함
    public static final String HEADER = "Authorization";

    @Value("${token.secret.key}")
    private String SECRET;

    public  String create(MemberDTO user,boolean refresh) {
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt( refresh ? new Date(System.currentTimeMillis() + REFRESH_EXP) : new Date(System.currentTimeMillis() + EXP))
                .withClaim("username", user.getUsername())
                .withClaim("name",user.getName())
                .withClaim("role", user.getRole().toString())
                .withClaim("image",user.getFileName())
                .withClaim("position",user.getPositionName())
                .withClaim("department",user.getDepartmentName())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC512(SECRET));

        if(!refresh) return TOKEN_PREFIX + jwt;
        else return jwt;
    }
    public DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {

        DecodedJWT decodedJWT = JWT
                .require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwt);

        return decodedJWT;
    }


}

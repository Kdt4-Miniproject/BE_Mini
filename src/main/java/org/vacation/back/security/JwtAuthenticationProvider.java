package org.vacation.back.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.exception.PasswordNotMatchException;
import org.vacation.back.security.service.MemberDetailsService;

import java.lang.reflect.Member;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider  implements AuthenticationProvider {


    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;

    // 이거 어짜피 시큐리티에서 객체 생성해야 하므로 거기서 빈주입 받아 쓰는 것이 더 좋을듯함
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;

            MemberDTO loadedUser =  memberDetailsService.loadUserByUsername(token.getPrincipal().getUsername());

            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }

            additionalAuthenticationChecks(loadedUser,authentication);

            return createSuccessAuthentication(loadedUser,authentication);
        }catch (UsernameNotFoundException e){
            log.error("USERNAME NOT VALID");
            return null;
        }catch (PasswordNotMatchException e){
            log.error("PASSWORD NOT VALID");
            return null;
        }

    }

    protected Authentication createSuccessAuthentication(MemberDTO memberDTO,Authentication authentication){
        return new JwtAuthenticationToken(memberDTO,authentication.getCredentials().toString());
    }
    protected void additionalAuthenticationChecks(MemberDTO memberDTO,
                                                  Authentication authentication){
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            return ; //인증 포기
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!this.passwordEncoder.matches(presentedPassword, memberDTO.getPassword())) {
            log.debug("Failed to authenticate since password does not match stored value");
            throw new PasswordNotMatchException("비밀번호 틀림");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == JwtAuthenticationToken.class;
    }
}

package org.vacation.back.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.vacation.back.dto.common.MemberDTO;

import java.util.Collection;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationToken  implements Authentication {

    private MemberDTO principal;
    private String credentials;	//비번
    private String details;	//정보
    private boolean authenticated;	//인증이 완료되면  true


    public JwtAuthenticationToken(MemberDTO memberDTO ,String credentials){
        this.principal = memberDTO;
        this.credentials = credentials;
        setAuthenticated(true);
    }
    public JwtAuthenticationToken(String username ,String credentials){
        this.principal = MemberDTO.builder().username(username).build();
        this.credentials = credentials;
        setAuthenticated(true);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(principal.getRole().toString()));
    }
    @Override
    public String getName() {
        return null;
    }

}

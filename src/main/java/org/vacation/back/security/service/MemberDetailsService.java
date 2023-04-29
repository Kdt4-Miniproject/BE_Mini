package org.vacation.back.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.vacation.back.domain.Member;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberDetailsService {

    private final MemberRepository memberRepository;
    public MemberDTO loadUserByUsername(String username)throws UsernameNotFoundException{

        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
        return  MemberDTO.toDTO(member);
    }
}

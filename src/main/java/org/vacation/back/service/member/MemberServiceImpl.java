package org.vacation.back.service.member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public boolean exist(String username) {
        return memberRepository.exist(username);
    }
}

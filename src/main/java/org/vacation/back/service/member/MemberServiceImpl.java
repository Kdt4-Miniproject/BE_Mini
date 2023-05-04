package org.vacation.back.service.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.domain.Member;
import org.vacation.back.dto.request.member.RegisterMemberDTO;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.service.MemberService;

import java.time.Year;
import java.util.Calendar;
import java.util.Random;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean exist(String username) {
        return memberRepository.exist(username);
    }

    @Override
    public boolean join(RegisterMemberDTO dto) {

        try{

            Integer year = Year.now().getValue() + 1;
            Integer temp  = Integer.parseInt(dto.getJoiningDay().substring(0,4));

            Integer years = year - temp;

            dto.setYears(years.toString());
            Random random = new Random();
            Integer randomNumber = random.nextInt(9000) + 1000;
            String assignNumber = Year.now().getValue() + randomNumber.toString();

            memberRepository.existsByEmployNumber(assignNumber);

            Member member = dto.toEntity();


            member.changePassword(passwordEncoder.encode(dto.getPassword()));
          //  member.assignEmNumber();
            Member data = memberRepository.save(member);

            System.out.println(data);
            System.out.println(data.getCreatedAt());
            System.out.println(data.getUpdatedAt());
            return true;
        }catch (Exception e){
            log.error("Error during storage function");
        }
        return false;
    }
}

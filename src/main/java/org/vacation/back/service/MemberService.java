package org.vacation.back.service;

import org.vacation.back.dto.request.member.RegisterMemberDTO;

public interface MemberService {

    public boolean exist(String username);


    public boolean join(RegisterMemberDTO dto);
}

package org.vacation.back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vacation.back.common.Search;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.dto.request.member.*;
import org.vacation.back.dto.response.PageResponseDTO;
import org.vacation.back.dto.response.VacationResponseDTO;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    public boolean exist(String username);


    public boolean join(RegisterMemberDTO dto);


    public PageResponseDTO<?> pageMember(Search text, String keyword, int page, int size);


    public MemberDTO findByDetail(String username);


    public boolean memberModify(MemberModifyDTO dto,String username);

    public boolean adminModify(AdminMemberModifyRequest memberModifyRequest);


    public boolean adminRoleModify(RoleChangeRequest request);


    public PageResponseDTO<?> deactivatedList(Search text, String keyword, int page, int size);

    public boolean adminStatusModify(AdminStatusModifyRequest request);

    public boolean changePwd(String username , PasswordModifyRequest passwordModifyRequest);

    public boolean memberRemove(String username);

    public PageResponseDTO<?> vacationFindByDepartment(Pageable pageable, String departmentName);
}

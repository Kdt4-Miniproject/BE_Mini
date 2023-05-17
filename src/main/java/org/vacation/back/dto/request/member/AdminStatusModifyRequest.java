package org.vacation.back.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.MemberStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminStatusModifyRequest {


    private String username;

    private MemberStatus memberStatus;


}


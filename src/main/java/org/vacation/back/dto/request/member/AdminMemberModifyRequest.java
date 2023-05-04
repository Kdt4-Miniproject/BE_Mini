package org.vacation.back.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.domain.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMemberModifyRequest {


    private String username;


    private String fileName;

    private String email;


    private String oldPassword;

    private String newPassword;
}

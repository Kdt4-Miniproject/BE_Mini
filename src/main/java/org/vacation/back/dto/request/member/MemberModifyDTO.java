package org.vacation.back.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberModifyDTO {


    private String fileName;

    private String email;


    private String oldPassword;

    private String newPassword;



}

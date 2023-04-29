package org.vacation.back.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMemberDTO {


    private String username; //id
    private String password; //pw
    private String name; //이름
    private String email; //id에 따른 email
    private String birthDate; //생년월일
    private String years; // 몇년차 토탈
    private String department;
    private String position;



}

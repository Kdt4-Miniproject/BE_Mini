package org.vacation.back.dto.request.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMemberDTO {


    private String username; //id
    private String password; //pw

    private String fileName;
    private String departmentName;  //FK
    private String positionName;    //FK


    private String phoneNumber;

    private String name; //이름
    private String email; //id에 따른 email
    private String birthDate; //생년월일

    private String joiningDay;

    @JsonIgnore
    private String years; // 몇년차 토탈



}

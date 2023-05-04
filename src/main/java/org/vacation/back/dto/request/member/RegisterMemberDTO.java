package org.vacation.back.dto.request.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Role;

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



    public Member toEntity(){
        return Member.builder()
                .username(this.username)
                .memberStatus(MemberStatus.WAITING)
                .email(this.email)
                .role(Role.STAFF)
                .birthdate(this.birthDate)
                .phoneNumber(this.phoneNumber)
                .departmentName(this.departmentName)
                .positionName(this.positionName)
                .fileName(this.fileName)
                .name(this.name)
                .joiningDay(LocalDate.parse(this.joiningDay))
                .totalYears(Integer.parseInt(this.years))
                .phoneNumber(this.phoneNumber)
                .build();
    }

}

package org.vacation.back.dto.request.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMemberDTO {


    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{6,}$") //6자 이상 영어+ 숫자
    private String username; //id

    @NotEmpty
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]*[a-zA-Z][a-zA-Z\\d]*$") //영서+숫자 or 영어
    private String password; //pw

    @NotEmpty
    private String fileName;
    @NotEmpty
    private String departmentName;  //FK
    @NotEmpty
    private String positionName;    //FK

    @NotEmpty
    @Pattern(regexp = "^01[0-9]{8,9}$")
    private String phoneNumber;

    @NotEmpty
    @Size(min = 2, max = 4)
    private String name; //이름
    @NotEmpty
    @Email
    private String email; //id에 따른 email

    @NotEmpty
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "올바른 날짜 형식(yyyy-MM-dd)이 아닙니다.")
    private String birthDate; //생년월일

    @NotEmpty
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "올바른 날짜 형식(yyyy-MM-dd)이 아닙니다.")
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
                .fileName(this.fileName)
                .name(this.name)
                .joiningDay(LocalDate.parse(this.joiningDay))
                .totalYears(Integer.parseInt(this.years))
                .phoneNumber(this.phoneNumber)
                .build();
    }

}

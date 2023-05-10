package org.vacation.back.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO{


    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private Role role;

    private String fileName;
    private String email;

    private String employeeNumber;

    private String phoneNumber;

    private String joiningDay;
    private String birthDate;
    private Integer years;
    private String name;
    private LocalDateTime updatedAt;

    private String positionName;

    private String departmentName;


    public static MemberDTO toDTO(Member member){

        return MemberDTO.builder()
                .username(member.getUsername() != null ? member.getUsername() : "")
                .password(member.getPassword() != null ? member.getPassword() : "")
                .role(member.getRole() != null ? member.getRole() : Role.STAFF)
                .email(member.getEmail() != null ? member.getEmail() : "")
                .employeeNumber(member.getEmployeeNumber() != null ? member.getEmployeeNumber() : "")
                .phoneNumber(member.getPhoneNumber() != null ?member.getPhoneNumber():"")
                .fileName(member.getFileName() != null ? member.getFileName() : "")
                .birthDate(member.getBirthdate() != null ? member.getBirthdate() : "")
                .name(member.getName() != null ? member.getName() : "")
                .joiningDay(member.getJoiningDay() != null ? member.getJoiningDay().toString() : "")
                .years(member.getTotalYears() != null ? member.getTotalYears() : 0)
                .updatedAt(member.getUpdatedAt() != null ? member.getUpdatedAt() : LocalDateTime.MIN)
                .positionName(member.getPosition() != null ? member.getPosition().getPositionName() : "")
                .departmentName(member.getDepartment() != null ? member.getDepartment().getDepartmentName() : "")
                .build();
    }





}

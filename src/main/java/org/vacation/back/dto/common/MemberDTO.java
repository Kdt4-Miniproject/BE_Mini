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

    private String birthDate;
    private String years;
    private String name;
    private LocalDateTime updatedAt;


    public static MemberDTO toDTO(Member member){

        return MemberDTO.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .role(member.getRole())
                .email(member.getEmail())
                .employeeNumber(member.getEmployeeNumber())
                .phoneNumber(member.getPhoneNumber())
                .birthDate(member.getBirthdate())
                .years(member.getYears())
                .name(member.getName())
                .updatedAt(member.getUpdatedAt())
                .build();
    }




}

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

    private String password;

    private String email;

    private Role role;


    private String employeeNumber;

    private String phoneNumber;

    private String birthdate;

    private String department;


    private String position;

    private String years;

    private String name;

    private boolean deleted;
}

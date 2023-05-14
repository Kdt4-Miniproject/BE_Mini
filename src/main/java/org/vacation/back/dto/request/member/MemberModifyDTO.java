package org.vacation.back.dto.request.member;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberModifyDTO {


    @NotEmpty
    private String fileName;


    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 2, max = 4)
    private String name;


    @NotEmpty
    @Pattern(regexp = "^01[0-9]{8,9}$")
    private String phoneNumber;

    private String oldPassword;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]*[a-zA-Z][a-zA-Z\\d]*$")
    private String newPassword;





}

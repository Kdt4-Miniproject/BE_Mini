package org.vacation.back.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameRequestDTO {

    @Email
    @NotEmpty
    @NotBlank
    private String username;
}

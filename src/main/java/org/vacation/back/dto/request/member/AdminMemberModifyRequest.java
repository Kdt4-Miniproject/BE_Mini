package org.vacation.back.dto.request.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMemberModifyRequest {


    private String username;
    private String name;

    private String email;
    private String phoneNumber;
    private String birthDate;
    private String joiningDay;

    @JsonIgnore
    private String years;

    private String fileName;

}

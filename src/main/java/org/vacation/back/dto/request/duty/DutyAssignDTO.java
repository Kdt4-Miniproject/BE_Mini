package org.vacation.back.dto.request.duty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyAssignDTO {

    private Long id;
    private String memberUsername;
    private LocalDate day;
    private DutyStatus status;
    public Duty toEntity(Member member){

        return Duty.builder()
                .member(member)
                .day(this.day)
                .status(this.status)
                .build();
    }
}

package org.vacation.back.dto.request.duty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutySaveRequestDTO {

    private String username;
    @NotNull(message = "당직 날짜가 비어 있습니다.")
    private LocalDate day;
    private DutyStatus status;
    public Duty toEntity(Member member){

        return Duty.builder()
                .member(member)
                .day(this.day)
                .status(DutyStatus.WAITING)
                .build();
    }
}

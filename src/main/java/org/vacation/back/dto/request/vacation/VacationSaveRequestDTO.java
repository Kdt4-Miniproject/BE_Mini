package org.vacation.back.dto.request.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacationSaveRequestDTO {

    private String userName; //id

    private LocalDate start;

    private LocalDate end;

    private VacationStatus status;

    public Vacation toEntity(Member member){

        return Vacation.builder()
                .member(member)
                .start(this.start)
                .end(this.end)
                .status(this.status)
                .build();
    }
}

package org.vacation.back.dto.request.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.Vacation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacationSaveRequestDTO {

    @NotNull(message = "휴가 시작일자가 비어 있습니다.")
    private LocalDate start;

    @NotNull(message = "연차 끝 날짜가 비어 있습니다.")
    private LocalDate end;

    public Vacation toEntity(Member member){

        return Vacation.builder()
                .member(member)
                .start(this.start)
                .end(this.end)
                .status(VacationStatus.WAITING)
                .build();
    }
}

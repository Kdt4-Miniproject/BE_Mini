package org.vacation.back.dto.request.vacation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacationModifyDTO {

    @NotBlank(message = "연차 ID 정보가 비어 있습니다.")
    private Long id;

    @NotBlank(message = "휴가 시작일자가 비어 있습니다.")
    private LocalDate start;

    @NotBlank(message = "연차 끝 날짜가 비어 있습니다.")
    private LocalDate end;
}

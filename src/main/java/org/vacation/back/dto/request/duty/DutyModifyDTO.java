package org.vacation.back.dto.request.duty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyModifyDTO {

    @NotBlank(message = "당직 ID 정보를 입력하세요")
    private Long id;
    @NotNull(message = "변경할 당직 날짜를 입력하세요")
    private LocalDate day;



}

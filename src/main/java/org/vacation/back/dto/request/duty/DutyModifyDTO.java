package org.vacation.back.dto.request.duty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyModifyDTO {

    private Long id;

    private LocalDate day;



}

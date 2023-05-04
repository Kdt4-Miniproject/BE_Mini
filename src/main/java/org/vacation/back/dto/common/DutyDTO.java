package org.vacation.back.dto.common;

import lombok.*;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.domain.Duty;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DutyDTO {

    private Long id;


    private LocalDate day;

    private boolean deleted;

    private DutyStatus status;


    public static DutyDTO toDTO(Duty duty){
        return DutyDTO.builder()
                .id(duty.getId())
                .day(duty.getDay())
                .build();
    }
}

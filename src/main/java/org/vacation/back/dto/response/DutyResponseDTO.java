package org.vacation.back.dto.response;
import lombok.*;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.domain.Duty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DutyResponseDTO {
    private Long id;
    private String memberName;

    private LocalDate day;

    private DutyStatus status;

    private String departmentName;

    private LocalDateTime createdAt;
    private String positionName;
    private String employeeNumber;



    public static DutyResponseDTO toDTO(Duty duty){

        return DutyResponseDTO.builder()
                .id(duty.getId())
                .memberName(duty.getMember().getUsername())
                .day(duty.getDay())
                .createdAt(duty.getCreatedAt())
                .status(duty.getStatus())
                .departmentName(duty.getMember().getDepartment().getDepartmentName())
                .positionName(duty.getMember().getPosition().getPositionName())
                .employeeNumber(duty.getMember().getEmployeeNumber())
                .build();
    }
}

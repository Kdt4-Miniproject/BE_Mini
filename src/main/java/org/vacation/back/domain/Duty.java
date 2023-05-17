package org.vacation.back.domain;

import lombok.*;
import org.vacation.back.common.DutyStatus;
import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Duty extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "duty_day")
    private LocalDate day;


    @Enumerated(EnumType.STRING)
    private DutyStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "original_day")
    private LocalDate originalDay;


    public void modifyDuty(LocalDate day){
        this.day = day;
    }
    public void setStatus(Enum dutyStatus){
        this.status = (DutyStatus) dutyStatus;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

}

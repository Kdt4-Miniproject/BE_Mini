package org.vacation.back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.VacationStatus;

import javax.persistence.*;

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
    private String day;


    @Enumerated(EnumType.STRING)
    @Column(name = "duty_status")
    private VacationStatus status;


    @ManyToOne
    @JoinColumn(name = "member_username")
    private Member member;

}

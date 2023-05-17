package org.vacation.back.domain;

import lombok.*;
import org.vacation.back.common.VacationStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vacation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "start_date")
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;


    @Enumerated(EnumType.STRING)
    private VacationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void modifyVacation(LocalDate start, LocalDate end){
        this.status=VacationStatus.UPDATE_WAITING;
        this.start=start;
        this.end=end;
    }
}

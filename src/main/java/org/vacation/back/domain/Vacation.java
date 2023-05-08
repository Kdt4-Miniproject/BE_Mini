package org.vacation.back.domain;

import lombok.*;
import net.bytebuddy.asm.Advice;
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

    @ManyToOne
    @JoinColumn(name = "member_username", insertable = false, updatable = false)
    private Member member;

    @Column(name = "member_username")
    private String memberName;

    public void modifyVacation(LocalDate start, LocalDate end){
        this.start=start;
        this.end=end;
    }
}

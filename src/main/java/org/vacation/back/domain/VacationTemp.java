package org.vacation.back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class VacationTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_username")
    private Member member;

    @OneToOne
    private Vacation vacation;
    @Column(name = "start_date")
    private String start;
    @Column(name = "end_date")
    private String end;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private VacationStatus status;
}

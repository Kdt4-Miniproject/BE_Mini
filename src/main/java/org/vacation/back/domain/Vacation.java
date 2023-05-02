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
public class Vacation extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "temp_id")
    private VacationTemp vacationTemp;

    @Column(name = "start_date")

    private String start;

    @Column(name = "end_date")
    private String end;

    private boolean deleted;
    @Enumerated(EnumType.STRING)
    private VacationStatus status;
}

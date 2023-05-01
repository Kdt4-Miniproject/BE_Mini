package org.vacation.back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.VacationStatus;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vacation extends BaseEntity{
    @Id
    Long id;

    String username;

    String start;

    String end;

    boolean deleted;

    VacationStatus status;
}

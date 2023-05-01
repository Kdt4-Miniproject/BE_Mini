package org.vacation.back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.PositionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Position extends BaseEntity {

    @Id
    private Long id; // 직급 id

//    @Column(name = "member_number")
//    private String memberNumber; // 멤버 id
//
//    @Column(name = "department_number")
//    private String departmentNumber; // 부서 id

//    @Column(name = "position_name")
    @Enumerated(EnumType.STRING)
    private PositionStatus position; // 직급명 - 프론트

//    @Column(name = "position_vacation")
    private String vacation; // 직급에 맞는 휴가 개수

//    @Column(name = "position_years")
    private String years; // 현재 직급의 연차

    private boolean deleted;
}

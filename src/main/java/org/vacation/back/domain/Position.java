package org.vacation.back.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vacation.back.common.PositionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "position_tb")
@Entity
public class Position {

    @Id
    private String positionName; // 직급 id

    @Column(name = "position_vacation")
    private String vacation; // 직급에 맞는 휴가 개수

    @Column(name = "position_status")
    private PositionStatus status;

    @OneToMany(mappedBy = "position")
    @Builder.Default
    private List<Member> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "positionName")
    @Builder.Default
    private List<PositionAndDepartment> positionAndDepartments = new ArrayList<>();

    public void modify(String vacation){
        this.vacation = vacation;
    }

    public void setStatus(PositionStatus status) {
        this.status = status;
    }

}

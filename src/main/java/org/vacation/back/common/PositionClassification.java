package org.vacation.back.common;


import lombok.Getter;

@Getter
public enum PositionClassification {

    EMPLOYEE("사원"),ASSISTANTMANAGER("대리"),MANAGER("과장"),LEADER("팀장");


    private String positionName;

    PositionClassification(String name){
        this.positionName = name;
    }
}

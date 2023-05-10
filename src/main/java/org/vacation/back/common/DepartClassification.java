package org.vacation.back.common;

import lombok.Getter;

@Getter
public enum DepartClassification {

    DEVELOP("개발"),HUMAN("인사"),MARKETING("마케팅");

    private String departement;

    DepartClassification(String departement){
        this.departement = departement;
    }

}

package org.vacation.back.exception;

public class DepartmentVacationLimitCheckException extends RuntimeException{
    public DepartmentVacationLimitCheckException(String message) {
        super(message);
    }
}

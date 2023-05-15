package org.vacation.back.exception;

public class DepartmentDuplicateException extends RuntimeException{
    public DepartmentDuplicateException(String message) {
        super(message);
    }
}

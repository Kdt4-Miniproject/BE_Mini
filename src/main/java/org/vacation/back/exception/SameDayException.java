package org.vacation.back.exception;

public class SameDayException extends RuntimeException{
    public SameDayException(String message) {
        super(message);
    }
}


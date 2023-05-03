package org.vacation.back.exception;

public class EmailNotValidException extends RuntimeException{

    public EmailNotValidException(String msg){
        super(msg);
    }
}

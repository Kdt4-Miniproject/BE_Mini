package org.vacation.back.exception;

public class PasswordNotMatchException extends RuntimeException{

    public PasswordNotMatchException(String msg){
        super(msg);
    }
}

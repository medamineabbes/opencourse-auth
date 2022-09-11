package com.opencourse.authusermanagement.exceptions;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(){
        super("expired or invalid jwt");
    }
}

package com.opencourse.authusermanagement.exceptions;

public class CustomAuthenticationException extends RuntimeException{
    public CustomAuthenticationException(){
        super("authentication error");
    }
    public CustomAuthenticationException(String message){
        super(message);
    }
}

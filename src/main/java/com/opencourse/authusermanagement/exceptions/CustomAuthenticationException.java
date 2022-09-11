package com.opencourse.authusermanagement.exceptions;

public class CustomAuthenticationException extends RuntimeException{
    public CustomAuthenticationException(){
        super();
    }
    public CustomAuthenticationException(String message){
        super(message);
    }
}

package com.opencourse.authusermanagement.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    
    public EmailAlreadyExistsException(String email){
        super("email : " + email + " is already used in an other account");
    }
}

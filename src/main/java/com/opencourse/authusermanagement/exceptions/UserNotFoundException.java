package com.opencourse.authusermanagement.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("user with id : " + id + " not found");
    }
}

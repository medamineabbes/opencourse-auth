package com.opencourse.authusermanagement.externalservices;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.opencourse.authusermanagement.exceptions.CustomAuthenticationException;

//@SpringBootTest
public class GoogleServiceTest {
    
    private GoogleService service;
    
    @BeforeEach
    public void init(){
        service=new GoogleService();
        service.init();
    }

    @Test
    @DisplayName("should throw authentication exception")
    public void getUserDataTest(){
        assertThrows(CustomAuthenticationException.class,()->{
            service.getUserData("token");
        });
    }
}

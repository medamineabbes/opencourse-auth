package com.opencourse.authusermanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.entities.User;
import com.opencourse.authusermanagement.security.JwtAuthentication;
import com.opencourse.authusermanagement.security.JwtProvider;

public class JwtProviderTest{

    private JwtProvider provider;

    @BeforeEach
    public void init(){
        provider=new JwtProvider();
        provider.init();
    }

    @Test
    @DisplayName("")
    public void createTokenTest(){
        User amine=new User();
        amine.setEmail("amineabbes02@gmail.com");
        amine.setId(122L);
        amine.setFirstname("amine");
        amine.setLastname("abbes");
        amine.setImageUrl("imageUrl");
        amine.setRole(Role.MENTOR);
        
        String token=provider.createToken(amine);
        JwtAuthentication auth=(JwtAuthentication) provider.getAuthentication(token);

        assertEquals(amine.getId(),auth.getId());
        assertEquals(amine.getEmail(),auth.getName());
        assertTrue(auth.getAuthorities().contains(new SimpleGrantedAuthority(amine.getRole().toString())));
    }

    @Test
    @DisplayName("valid token")
    public void validateTokenTest(){
        User amine=new User();
        amine.setEmail("amineabbes02@gmail.com");
        amine.setId(122L);
        amine.setFirstname("amine");
        amine.setLastname("abbes");
        amine.setImageUrl("imageUrl");
        amine.setRole(Role.MENTOR);
        String token=provider.createToken(amine);

        assertTrue(provider.isValid(token));
    }


    @Test
    @DisplayName("not valid token")
    public void validateTokenTest2(){
        assertFalse(provider.isValid("zefzef.qfzeffedv.zegeqrhqebcbqfe"));
    }

}
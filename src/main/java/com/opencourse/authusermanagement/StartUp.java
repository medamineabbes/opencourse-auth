package com.opencourse.authusermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.opencourse.authusermanagement.dtos.UserDataDto;
import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.services.AuthenticationService;

import lombok.extern.slf4j.Slf4j;


//@Component
@Slf4j
public class StartUp implements CommandLineRunner{

    @Autowired
    AuthenticationService service;

    @Override
    public void run(String... args) throws Exception {
        UserDataDto dto=new UserDataDto();
        dto.setEmail("medamineabbes2@gmail.com");
        dto.setFirstname("amine");
        dto.setLastname("abbes");
        dto.setImageUrl("imageUrl");
        dto.setPassword("password");
        dto.setRole(Role.STUDENT.toString());
        service.signup(dto, null);
        log.info("user added to database");
    }
    
}

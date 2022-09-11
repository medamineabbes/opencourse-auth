package com.opencourse.authusermanagement.dtos.mappers;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.opencourse.authusermanagement.dtos.UserDataDto;
import com.opencourse.authusermanagement.entities.Role;
import com.opencourse.authusermanagement.entities.User;

@Component
public class UserDataMapper {
    
    public User toUser(UserDataDto dto){
        User user=new User();
        user.setConfirmationRequests(new ArrayList<>());
        user.setEmail(dto.getEmail());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setPassword(dto.getPassword());
        user.setRequests(new ArrayList<>());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setImageUrl(dto.getImageUrl());
        return user;
    }
    
}

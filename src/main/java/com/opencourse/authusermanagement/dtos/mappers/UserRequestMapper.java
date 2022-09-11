package com.opencourse.authusermanagement.dtos.mappers;

import org.springframework.stereotype.Component;

import com.opencourse.authusermanagement.dtos.UserRequestDto;
import com.opencourse.authusermanagement.entities.User;

@Component
public class UserRequestMapper {
    
    public UserRequestDto toUserRequestDto(User user){
        UserRequestDto dto=new UserRequestDto();
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setId(user.getId());
        dto.setLastname(user.getLastname());
        dto.setRole(user.getRole().toString());
        dto.setImageUrl(user.getImageUrl());
        return dto;
    }
}

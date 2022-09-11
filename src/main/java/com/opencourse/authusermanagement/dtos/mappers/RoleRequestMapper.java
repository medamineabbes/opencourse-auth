package com.opencourse.authusermanagement.dtos.mappers;

import org.springframework.stereotype.Component;

import com.opencourse.authusermanagement.dtos.RoleRequestsDto;
import com.opencourse.authusermanagement.entities.MentorTeacherRequest;

@Component
public class RoleRequestMapper {
    
    public RoleRequestsDto toDto(MentorTeacherRequest request){
        RoleRequestsDto dto=new RoleRequestsDto();
        dto.setCvId(request.getCvId());
        dto.setDate(request.getDate());
        dto.setId(request.getId());
        dto.setIsAccepted(request.getIsAccepted());
        dto.setUserId(request.getUser().getId());
        return dto;
    }
}

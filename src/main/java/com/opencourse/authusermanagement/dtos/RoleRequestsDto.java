package com.opencourse.authusermanagement.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoleRequestsDto {
    
    private String id;
    private LocalDateTime date;
    private Boolean isAccepted;
    private String cvId;
    private Long userId;
}

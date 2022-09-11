package com.opencourse.authusermanagement.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class MentorTeacherRequest {

    @Id
    private String id;
    private LocalDateTime date;
    private Boolean isAccepted;
    private String cvId;
    
    @DBRef
    private User user;
}

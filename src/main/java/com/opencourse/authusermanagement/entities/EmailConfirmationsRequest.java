package com.opencourse.authusermanagement.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class EmailConfirmationsRequest {
    @Id
    private String id;
    private String code;
    private LocalDateTime sentAt;

    //user
    @DBRef
    private User user;
}

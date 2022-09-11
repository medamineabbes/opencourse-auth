package com.opencourse.authusermanagement.entities;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class User {

    @Transient
    public static final String sequenceNama="sequence";

    @Id
    private Long id;
    
    @NotBlank(message = "firstname is required")
    @NotEmpty(message = "firstname is required")
    private String firstname;

    @NotBlank(message = "lastname is required")
    @NotEmpty(message = "lastname is required")
    private String lastname;

    @NotBlank(message = "email is required")
    @NotEmpty(message = "email is required")
    @Email
    @Indexed(unique = true)
    private String email;

    private String password;

    private Role role;

    private String imageUrl;
    //email confirmation
    private Boolean isConfirmed;

    //user is active 
    private Boolean isActive;

    //user is banned (banned by admin)
    private Boolean isBanned;

    //email confiramations
    @DBRef
    private List<EmailConfirmationsRequest> confirmationRequests;

    //role ugrade 
    @DBRef
    private List<MentorTeacherRequest> requests;

    //mentor
    @DBRef 
    private User mentor;

    //student
    @DBRef
    private List<User> students;

}

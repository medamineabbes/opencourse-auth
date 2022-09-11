package com.opencourse.authusermanagement.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

// for user signup
@Data
public class UserDataDto {
        
    @NotBlank(message = "firstname is required")
    @NotEmpty(message = "firstname is required")
    private String firstname;

    @NotBlank(message = "lastname is required")
    @NotEmpty(message = "lastname is required")
    private String lastname;

    @NotBlank(message = "email is required")
    @NotEmpty(message = "email is required")
    @Email
    private String email;

    private String password;

    private String role;

    private String imageUrl;
}

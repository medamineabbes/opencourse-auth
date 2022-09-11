package com.opencourse.authusermanagement.dtos;

import lombok.Data;

// for user search
@Data
public class UserRequestDto {
    
    private Long id;
    
    private String firstname;

    private String lastname;

    private String email;

    private String role;

    //add to mapper
    private String imageUrl;
}

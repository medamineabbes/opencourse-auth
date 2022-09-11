package com.opencourse.authusermanagement.dtos;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDataWithCvDto {

    @NotNull
    private UserDataDto userData;
    private String cvId;
}

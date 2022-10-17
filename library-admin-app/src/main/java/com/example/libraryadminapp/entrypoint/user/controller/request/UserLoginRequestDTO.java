package com.example.libraryadminapp.entrypoint.user.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginRequestDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}

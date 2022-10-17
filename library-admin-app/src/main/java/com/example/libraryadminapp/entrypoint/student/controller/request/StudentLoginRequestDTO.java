package com.example.libraryadminapp.entrypoint.student.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class StudentLoginRequestDTO {

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String password;
}

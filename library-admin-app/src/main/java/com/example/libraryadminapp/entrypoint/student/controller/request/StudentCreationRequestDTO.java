package com.example.libraryadminapp.entrypoint.student.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class StudentCreationRequestDTO {

    @NotBlank
    private String studentName;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String academicYear;

    @NotBlank
    private String facultyName;
}

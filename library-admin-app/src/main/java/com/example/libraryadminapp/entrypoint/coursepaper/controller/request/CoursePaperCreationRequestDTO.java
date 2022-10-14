package com.example.libraryadminapp.entrypoint.coursepaper.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class CoursePaperCreationRequestDTO {

    @NotBlank
    private String coursePaperName;

    @NotBlank
    private String subjectName;

    @NotBlank
    private String professorName;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String academicYear;

    @NotBlank
    private String facultyName;
}

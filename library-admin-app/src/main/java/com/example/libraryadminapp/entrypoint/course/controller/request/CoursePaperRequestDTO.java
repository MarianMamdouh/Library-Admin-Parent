package com.example.libraryadminapp.entrypoint.course.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class CoursePaperRequestDTO {

    @NotBlank
    private String coursePaperName;
    @NotBlank
    private String subjectName;
    @NotBlank
    private String professorName;
    @NotBlank
    private String academicYear;
    @NotBlank
    private String facultyName;
    @NotNull
    private BigDecimal price;
}

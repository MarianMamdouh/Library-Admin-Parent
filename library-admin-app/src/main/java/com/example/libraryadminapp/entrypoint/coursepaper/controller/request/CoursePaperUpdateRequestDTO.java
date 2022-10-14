package com.example.libraryadminapp.entrypoint.coursepaper.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class CoursePaperUpdateRequestDTO {

    @NotBlank
    private String coursePaperName;

    private String subjectName;

    private String professorName;

    private BigDecimal price;

    private String academicYear;

    private String facultyName;

}

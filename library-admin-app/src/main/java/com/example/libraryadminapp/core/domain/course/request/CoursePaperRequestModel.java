package com.example.libraryadminapp.core.domain.course.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Builder
public class CoursePaperRequestModel {

    private String coursePaperName;
    private String subjectName;
    private String professorName;
    private String academicYear;
    private String facultyName;
    private BigDecimal price;
}

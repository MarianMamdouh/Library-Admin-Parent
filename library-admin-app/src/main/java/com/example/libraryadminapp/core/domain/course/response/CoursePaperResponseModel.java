package com.example.libraryadminapp.core.domain.course.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CoursePaperResponseModel {

    private final String coursePaperName;
    private final String subjectName;
    private final String professorName;
    private final BigDecimal price;
    private final String academicYear;
    private final String facultyName;
}

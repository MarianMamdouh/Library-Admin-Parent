package com.example.libraryadminapp.core.domain.coursepaper.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CoursePaperCreationRequestModel {

    private final String coursePaperName;
    private final String subjectName;
    private final String professorName;
    private final BigDecimal price;
    private final String academicYear;
    private final String facultyName;
}

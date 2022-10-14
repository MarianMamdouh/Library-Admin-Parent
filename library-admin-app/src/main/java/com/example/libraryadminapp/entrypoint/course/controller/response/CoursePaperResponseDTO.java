package com.example.libraryadminapp.entrypoint.course.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class CoursePaperResponseDTO {

    private final String coursePaperName;
    private final String subjectName;
    private final String professorName;
    private final BigDecimal price;
    private final String academicYear;
    private final String facultyName;
}

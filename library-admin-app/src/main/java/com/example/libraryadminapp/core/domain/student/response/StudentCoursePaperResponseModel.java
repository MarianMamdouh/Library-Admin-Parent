package com.example.libraryadminapp.core.domain.student.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class StudentCoursePaperResponseModel {

    private String coursePaperName;
    private String subjectName;
    private String professorName;
    private BigDecimal price;
}

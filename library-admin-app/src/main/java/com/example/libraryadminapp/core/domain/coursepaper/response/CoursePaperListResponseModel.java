package com.example.libraryadminapp.core.domain.coursepaper.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CoursePaperListResponseModel {

    private final String coursePaperName;
    private final String subjectName;
    private final String professorName;
    private final BigDecimal price;
    private final Integer numOfCopies;
}

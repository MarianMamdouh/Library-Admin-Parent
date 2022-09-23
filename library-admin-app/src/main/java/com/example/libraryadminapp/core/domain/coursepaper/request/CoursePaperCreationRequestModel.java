package com.example.libraryadminapp.core.domain.coursepaper.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CoursePaperCreationRequestModel {

    private String coursePaperName;
    private String subjectName;
    private String professorName;
    private BigDecimal price;
}

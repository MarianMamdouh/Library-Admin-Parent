package com.example.libraryadminapp.core.domain.course.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CoursePaperRequestModel {

    private String coursePaperName;
    private BigDecimal price;
    private Integer numOfCopies;
}

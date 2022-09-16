package com.example.libraryadminparent.core.domain.course.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CourseCreationRequestModel {

    private String courseName;
    private BigDecimal pricePerSemester;
    private BigDecimal pricePerMonth;
    private String subjectName;
    private String professorName;
    private Integer maxNumberOfBookings;
}

package com.example.libraryadminparent.core.domain.course.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CourseListResponseModel {

    private final String courseName;
    private final BigDecimal pricePerSemester;
    private final BigDecimal pricePerMonth;
    private final String subjectName;
    private final String professorName;
    private final Integer maxNumberOfBookings;
}

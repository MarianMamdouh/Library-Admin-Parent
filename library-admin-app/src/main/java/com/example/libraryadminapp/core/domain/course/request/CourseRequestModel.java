package com.example.libraryadminapp.core.domain.course.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CourseRequestModel {

    private final String courseName;
    private final BigDecimal pricePerSemester;
    private final BigDecimal pricePerMonth;
    private final String subjectName;
    private final String professorName;
    private final Integer maxNumberOfBookings;
    private final List<CoursePaperRequestModel> coursePapers;
}

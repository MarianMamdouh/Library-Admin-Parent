package com.example.libraryadminapp.core.domain.student.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class StudentCourseResponseModel {

    private String courseName;
    private BigDecimal pricePerSemester;
    private BigDecimal pricePerMonth;
    private String subjectName;
    private String professorName;
    private Integer maxNumberOfBookings;
}

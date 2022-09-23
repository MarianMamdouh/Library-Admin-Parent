package com.example.libraryadminapp.entrypoint.course.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class CourseListResponseDTO {

    private String courseName;
    private BigDecimal pricePerSemester;
    private BigDecimal pricePerMonth;
    private String subjectName;
    private String professorName;
    private Integer maxNumberOfBookings;
}

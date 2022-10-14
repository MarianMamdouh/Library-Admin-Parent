package com.example.libraryadminapp.entrypoint.student.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class StudentCourseResponseDTO {

    private String courseName;
    private BigDecimal pricePerSemester;
    private BigDecimal pricePerMonth;
    private String subjectName;
    private String professorName;
}

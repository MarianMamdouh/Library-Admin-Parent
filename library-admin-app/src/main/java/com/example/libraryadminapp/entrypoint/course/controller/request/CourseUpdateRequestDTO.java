package com.example.libraryadminapp.entrypoint.course.controller.request;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
public class CourseUpdateRequestDTO {

    @NotBlank
    private String courseName;

    private BigDecimal pricePerSemester;

    private BigDecimal pricePerMonth;

    private String subjectName;

    private String professorName;

    private Integer maxNumberOfBookings;

    private List<CoursePaperRequestDTO> coursePapers;
}

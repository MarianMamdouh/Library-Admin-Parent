package com.example.libraryadminapp.entrypoint.course.controller.request;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
public class CourseCreationRequestDTO {

    @NotBlank
    private String courseName;

    @NotNull
    private BigDecimal pricePerSemester;

    @NotNull
    private BigDecimal pricePerMonth;

    @NotBlank
    private String subjectName;

    @NotBlank
    private String professorName;

    @NotBlank
    private String academicYear;

    @NotBlank
    private String facultyName;

    @NotNull
    @Valid
    private List<CourseSlotRequestDTO> courseSlots;

    @NotNull
    @Valid
    private List<CoursePaperRequestDTO> coursePapers;
}

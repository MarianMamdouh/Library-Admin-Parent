package com.example.libraryadminapp.core.domain.course.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class CourseListResponseModel {

    private final String courseName;
    private final BigDecimal pricePerSemester;
    private final BigDecimal pricePerMonth;
    private final String subjectName;
    private final String professorName;
    private final String academicYear;
    private final String facultyName;
    private final List<CourseSlotResponseModel> courseSlots;
    private final List<CoursePaperResponseModel> coursePapers;
    private final String courseStatus;

}

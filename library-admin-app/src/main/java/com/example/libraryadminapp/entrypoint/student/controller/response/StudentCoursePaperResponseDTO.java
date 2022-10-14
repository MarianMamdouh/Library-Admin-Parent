package com.example.libraryadminapp.entrypoint.student.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
public class StudentCoursePaperResponseDTO {

    private String coursePaperName;
    private String subjectName;
    private String professorName;
    private BigDecimal price;
}

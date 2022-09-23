package com.example.libraryadminapp.entrypoint.coursepaper.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class CoursePaperListResponseDTO {

    private String courseName;
    private String subjectName;
    private String professorName;
    private BigDecimal price;
    private Integer numOfCopies;}

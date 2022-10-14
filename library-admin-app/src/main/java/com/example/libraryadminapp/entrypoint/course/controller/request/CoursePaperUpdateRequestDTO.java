package com.example.libraryadminapp.entrypoint.course.controller.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CoursePaperUpdateRequestDTO {

    private String coursePaperName;

    private BigDecimal price;
}

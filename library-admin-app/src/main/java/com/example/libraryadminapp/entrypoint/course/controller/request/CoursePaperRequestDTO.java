package com.example.libraryadminapp.entrypoint.course.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class CoursePaperRequestDTO {

    @NotBlank
    private String coursePaperName;

    @NotNull
    private BigDecimal price;

    private Integer numOfCopies;
}

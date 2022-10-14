package com.example.libraryadminapp.entrypoint.course.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class CourseSlotRequestDTO {

    private Long id;

    @NotBlank
    private String day;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    private Integer maxNumberOfBookings;
}

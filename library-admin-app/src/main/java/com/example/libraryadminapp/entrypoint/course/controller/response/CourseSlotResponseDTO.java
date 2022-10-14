package com.example.libraryadminapp.entrypoint.course.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Builder
@Setter
@Getter
public class CourseSlotResponseDTO {

    private Long id;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxNumberOfBookings;
}

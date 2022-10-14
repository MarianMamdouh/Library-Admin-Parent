package com.example.libraryadminapp.core.domain.course.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class CourseSlotRequestModel {

    private final Long id;
    private final String day;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Integer maxNumberOfBookings;
}

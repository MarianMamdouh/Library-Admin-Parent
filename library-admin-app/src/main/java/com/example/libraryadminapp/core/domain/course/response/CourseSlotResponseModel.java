package com.example.libraryadminapp.core.domain.course.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Builder
@Getter
public class CourseSlotResponseModel {

    private final Long id;
    private final String day;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Integer maxNumberOfBookings;
    private final Integer currentNumberOfBookings;


}

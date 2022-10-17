package com.example.libraryadminapp.core.domain.course.utils;

public enum CourseStatus {

    ALREADY_BOOKED("Already Booked"),
    FULLY_BOOKED("Fully Booked"),
    AVAILABLE_TO_BOOK("Available to Book");
    private final String courseStatus;

    CourseStatus(final String courseStatus) {

        this.courseStatus = courseStatus;
    }
}

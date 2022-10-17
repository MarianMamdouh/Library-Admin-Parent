package com.example.libraryadminapp.core.domain.student.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentCreationRequestModel {

    private final String studentName;
    private final String mobileNumber;
    private final String password;
    private final String academicYear;
    private final String facultyName;

}

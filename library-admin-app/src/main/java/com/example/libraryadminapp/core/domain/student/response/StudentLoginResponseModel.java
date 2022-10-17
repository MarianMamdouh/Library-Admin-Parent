package com.example.libraryadminapp.core.domain.student.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentLoginResponseModel {

    private final String jwtToken;
    private final String studentName;
    private final String mobileNumber;
}
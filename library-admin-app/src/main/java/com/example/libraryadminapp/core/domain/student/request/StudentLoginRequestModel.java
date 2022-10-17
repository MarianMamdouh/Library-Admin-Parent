package com.example.libraryadminapp.core.domain.student.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentLoginRequestModel {

    private final String mobileNumber;
    private final String password;
}

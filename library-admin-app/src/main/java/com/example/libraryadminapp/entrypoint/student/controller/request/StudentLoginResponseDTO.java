package com.example.libraryadminapp.entrypoint.student.controller.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentLoginResponseDTO {

    private String jwtToken;
    private String studentName;
    private String mobileNumber;
}

package com.example.libraryadminapp.core.domain.student.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CoursePaymentInfoResponseModel {

    private String studentName;
    private Integer paymentNumber;
    private String courseName;
    private String coursePaperName;
}

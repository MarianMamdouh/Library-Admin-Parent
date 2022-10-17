package com.example.libraryadminapp.entrypoint.student.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CoursePaymentInfoResponseDTO {

    private String studentName;
    private Integer paymentNumber;
    private String courseName;
    private String coursePaperName;
    private String deliveryAddress;
}

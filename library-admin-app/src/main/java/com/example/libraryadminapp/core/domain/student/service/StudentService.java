package com.example.libraryadminapp.core.domain.student.service;

import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.response.CoursePaymentInfoResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCourseResponseModel;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    void createStudent(StudentCreationRequestModel studentCreationRequestModel);
    void verifyStudentMobileNumber(String studentMobileNumber) throws IOException;

    Integer assignCourseToStudent(String courseName, String studentName, Long courseSlotId);

    Integer assignCoursePaperToStudent(String coursePaperName, String studentName);

    List<StudentCourseResponseModel> getAllCoursesBookings(String studentName);

    List<StudentCoursePaperResponseModel> getAllCoursePapersBookings(String studentName);

    Page<CoursePaymentInfoResponseModel> getAllCoursesPaymentInfo();

    Page<CoursePaymentInfoResponseModel> getAllCoursePapersPaymentInfo();

    Page<CoursePaymentInfoResponseModel> searchByPaymentInfoNumber(Integer paymentInfoNumber);
}

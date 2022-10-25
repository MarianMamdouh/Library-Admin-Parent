package com.example.libraryadminapp.core.domain.student.service;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.request.StudentLoginRequestModel;
import com.example.libraryadminapp.core.domain.student.response.StudentLoginResponseModel;
import com.example.libraryadminapp.core.domain.student.response.CoursePaymentInfoResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCourseResponseModel;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    void createStudent(StudentCreationRequestModel studentCreationRequestModel) throws IOException;
    StudentLoginResponseModel login(StudentLoginRequestModel studentLoginModel);

    void verifyStudentOTP(String otp, String mobileNumber) throws IOException;

    void resendOTP(String mobileNumber) throws IOException;

    void setFCMToken(String fcmToken, String mobileNumber) throws IOException;

    Integer assignCourseToStudent(String courseName, String mobileNumber, Long courseSlotId);

    Integer assignCoursePaperToStudent(String coursePaperName, String mobileNumber, String deliveryAddress);

    List<StudentCourseResponseModel> getAllCoursesBookings(String mobileNumber);

    List<StudentCoursePaperResponseModel> getAllCoursePapersBookings(String mobileNumber);

    Page<CoursePaymentInfoResponseModel> getAllCoursesPaymentInfo();

    Page<CoursePaymentInfoResponseModel> getAllCoursePapersPaymentInfo();
    CoursePaymentInfoResponseModel searchByPaymentInfoNumber(Integer paymentInfoNumber);

    void deleteCoursePaperPaymentInfo(Integer paymentInfoNumber);

    void deleteCoursePaymentInfo(Integer paymentInfoNumber);

    void deleteFCMToken(String mobileNumber);

    Page<CoursePaymentInfoResponseModel> filterByDeliveryAddress();

    List<String> getStudentNotifications(String mobileNumber);

    void unassignStudentFromCourse(String courseName,String mobileNumber);

    void unassignStudentFromCoursePaper(String coursePaperName, String mobileNumber);

    Page<CoursePaymentInfoResponseModel> findAllByCoursePapersExists();

    Page<CoursePaymentInfoResponseModel> findAllByCourseSlotsExists();
}

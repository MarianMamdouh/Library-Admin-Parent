package com.example.libraryadminapp.entrypoint.student.facade;

import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentLoginRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentLoginResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.CoursePaymentInfoResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface StudentFacade {

    void createStudent(StudentCreationRequestDTO studentCreationRequestDTO) throws IOException;

    StudentLoginResponseDTO login(StudentLoginRequestDTO studentLoginDTO);

    void verifyStudentOTP(String otp, String mobileNumber) throws IOException;

    void setFCMToken(String fcmToken, String mobileNumber) throws IOException;

    Integer assignCourseToStudent(String courseName, String mobileNumber, Long courseSlotId);

    Integer assignCoursePaperToStudent(String coursePaperName, String mobileNumber, String deliveryAddress);

    List<StudentCourseResponseDTO> getAllCoursesBookings(String mobileNumber);

    List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(String mobileNumber);

    Page<CoursePaymentInfoResponseDTO> getAllCoursesPaymentInfo();

    Page<CoursePaymentInfoResponseDTO> getAllCoursePapersPaymentInfo();

    CoursePaymentInfoResponseDTO searchByPaymentInfoNumber(Integer paymentInfoNumber);

    void deleteCoursePaperPaymentInfo(Integer paymentInfoNumber);

    void deleteCoursePaymentInfo(Integer paymentInfoNumber);

    void deleteFCMToken(String mobileNumber);

    Page<CoursePaymentInfoResponseDTO> filterByDeliveryAddress();
}

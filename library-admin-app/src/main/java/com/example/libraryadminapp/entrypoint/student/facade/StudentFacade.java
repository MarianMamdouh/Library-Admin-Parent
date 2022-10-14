package com.example.libraryadminapp.entrypoint.student.facade;

import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.CoursePaymentInfoResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface StudentFacade {

    void createStudent(StudentCreationRequestDTO studentCreationRequestDTO);

    void verifyStudentMobileNumber(String studentMobileNumber) throws IOException;

    Integer assignCourseToStudent(String courseName, String studentName, Long courseSlotId);

    Integer assignCoursePaperToStudent(String coursePaperName, String studentName);

    List<StudentCourseResponseDTO> getAllCoursesBookings(String studentName);

    List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(String studentName);

    Page<CoursePaymentInfoResponseDTO> getAllCoursesPaymentInfo();

    Page<CoursePaymentInfoResponseDTO> getAllCoursePapersPaymentInfo();

    Page<CoursePaymentInfoResponseDTO> searchByPaymentInfoNumber(Integer paymentInfoNumber);

}

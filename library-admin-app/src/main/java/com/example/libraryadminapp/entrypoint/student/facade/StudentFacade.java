package com.example.libraryadminapp.entrypoint.student.facade;

import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;

import java.util.List;

public interface StudentFacade {

    void createStudent(StudentCreationRequestDTO studentCreationRequestDTO);

    void assignCourseToStudent(String courseName, String studentName, Long courseSlotId);

    void assignCoursePaperToStudent(String coursePaperName, String studentName);

    List<StudentCourseResponseDTO> getAllCoursesBookings(String studentName);

    List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(String studentName);

}

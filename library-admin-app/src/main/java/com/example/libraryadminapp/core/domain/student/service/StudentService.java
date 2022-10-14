package com.example.libraryadminapp.core.domain.student.service;

import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCourseResponseModel;

import java.util.List;

public interface StudentService {

    void createStudent(final StudentCreationRequestModel studentCreationRequestModel);

    void assignCourseToStudent(String courseName, String studentName, Long courseSlotId);

    void assignCoursePaperToStudent(String coursePaperName, String studentName);

    List<StudentCourseResponseModel> getAllCoursesBookings(String studentName);

    List<StudentCoursePaperResponseModel> getAllCoursePapersBookings(String studentName);

}

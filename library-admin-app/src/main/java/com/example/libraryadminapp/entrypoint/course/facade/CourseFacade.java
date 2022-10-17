package com.example.libraryadminapp.entrypoint.course.facade;

import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CoursePaperRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseSlotRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseFacade {

    void createCourse(CourseCreationRequestDTO courseCreationRequestDTO);

    void updateCourse(CourseUpdateRequestDTO courseUpdateRequestDTO);

    void addCoursePaperToCourse(String courseName, CoursePaperRequestDTO coursePaperRequestDTO);

    void deleteCoursePaperFromCourse(String courseName, String coursePaperName);

    void deleteCourseSlotFromCourse(String courseName, Long courseSlotId);

    void addCourseSlotToCourse(String courseName, CourseSlotRequestDTO courseSlotRequestDTO);

    void deleteCourse(final String courseName);
    Page<CourseListResponseDTO> getAllCourses();

    List<CourseListResponseDTO> searchCoursesByMobileNumber(String searchTerm, String mobileName);
    Page<CourseListResponseDTO> searchCourses(String searchName);

    List<CourseListResponseDTO> getAllAvailableCoursesForStudent(String mobileNumber);
}

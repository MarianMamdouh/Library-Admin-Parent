package com.example.libraryadminapp.entrypoint.course.facade;

import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import org.springframework.data.domain.Page;

public interface CourseFacade {

    void createCourse(CourseCreationRequestDTO courseCreationRequestDTO);

    void updateCourse(CourseUpdateRequestDTO courseUpdateRequestDTO);

    void deleteCourse(final String courseName);
    Page<CourseListResponseDTO> getAllCourses();

}

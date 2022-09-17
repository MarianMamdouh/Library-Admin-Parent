package com.example.libraryadminparent.entrypoint.course.facade;

import com.example.libraryadminparent.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.course.controller.response.CourseListResponseDTO;
import com.example.libraryadminparent.entrypoint.professor.controller.response.ProfessorListResponseDTO;
import org.springframework.data.domain.Page;

public interface CourseFacade {

    void createCourse(final CourseCreationRequestDTO courseCreationRequestDTO);

    Page<CourseListResponseDTO> getAllCourses();

}

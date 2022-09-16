package com.example.libraryadminparent.entrypoint.course.facade;

import com.example.libraryadminparent.entrypoint.course.controller.request.CourseCreationRequestDTO;

public interface CourseFacade {

    void createCourse(final CourseCreationRequestDTO courseCreationRequestDTO);
}

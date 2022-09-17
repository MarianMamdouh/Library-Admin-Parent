package com.example.libraryadminparent.core.domain.course.service;

import com.example.libraryadminparent.core.domain.course.request.CourseCreationRequestModel;
import com.example.libraryadminparent.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminparent.core.domain.professor.response.ProfessorListResponseModel;
import org.springframework.data.domain.Page;

public interface CourseService {

    void createCourse(CourseCreationRequestModel courseCreationRequestModel);

    Page<CourseListResponseModel> getAllCourses();

}

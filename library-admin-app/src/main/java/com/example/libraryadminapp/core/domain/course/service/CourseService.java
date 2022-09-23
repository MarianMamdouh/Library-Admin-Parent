package com.example.libraryadminapp.core.domain.course.service;

import com.example.libraryadminapp.core.domain.course.request.CourseRequestModel;
import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import org.springframework.data.domain.Page;

public interface CourseService {

    void createCourse(CourseRequestModel courseCreationRequestModel);

    void updateCourse(CourseRequestModel courseRequestModel);

    void deleteCourse(String courseName);

    Page<CourseListResponseModel> getAllCourses();

}

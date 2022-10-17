package com.example.libraryadminapp.core.domain.course.service;

import com.example.libraryadminapp.core.domain.course.request.CoursePaperRequestModel;
import com.example.libraryadminapp.core.domain.course.request.CourseRequestModel;
import com.example.libraryadminapp.core.domain.course.request.CourseSlotRequestModel;
import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    void createCourse(CourseRequestModel courseCreationRequestModel);

    void updateCourse(CourseRequestModel courseRequestModel);

    void addCoursePaperToCourse(String courseName, CoursePaperRequestModel coursePaperRequestModel);

    void deleteCoursePaperFromCourse(String courseName, String coursePaperName);

    void deleteCourseSlotFromCourse(String courseName, Long courseSlotId);

    void addCourseSlotToCourse(String courseName, CourseSlotRequestModel courseSlotRequestModel);

    void deleteCourse(String courseName);

    Page<CourseListResponseModel> getAllCourses();

    List<CourseListResponseModel> getAllAvailableCoursesForStudent(String mobileNumber);

    List<CourseListResponseModel> searchCoursesByMobileNumber(String searchTerm, String mobileStudent);

    Page<CourseListResponseModel> searchCourses(String searchTerm);

}

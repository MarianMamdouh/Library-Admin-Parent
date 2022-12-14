package com.example.libraryadminapp.core.domain.course.repository;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    Course save(Course course);

    Optional<Course> findByCourseName(String courseName);

    Page<Course> findAll();

    void deleteByCourseName(String courseName);

    Page<Course> findAllByCourseNameOrSubjectNameOrProfessorName(String searchName);

    List<Course> findAllByAcademicYearAndFacultyNameAndCourseNameOrSubjectNameOrProfessorName(String academicYear, String facultyName, String searchTerm);

    List<Course> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName);
}

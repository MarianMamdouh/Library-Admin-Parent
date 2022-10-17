package com.example.libraryadminapp.infrastructure.database.course;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseName(@Param("courseName") String courseName);

    Page<Course> findAllByCourseNameOrSubjectNameOrProfessorName(String courseName, String subjectName, String professorName, Pageable pageable);

    List<Course> findAllByAcademicYear_YearAndFaculty_Name(String academicYear, String facultyName);

    List<Course> findAllByAcademicYear_YearAndFaculty_NameAndCourseNameOrSubjectNameOrProfessorName(String academicYear, String facultyName, String courseName, String subjectName, String professorName);

    void deleteByCourseName(@Param("courseName") String courseName);
}
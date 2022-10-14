package com.example.libraryadminapp.core.domain.student.repository;

import com.example.libraryadminapp.core.domain.student.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);

    Optional<Student> findByStudentName(String studentName);

    List<Student> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName);

//
//    Page<Course> findAll();
//
//    void deleteByCourseName(String courseName);
}

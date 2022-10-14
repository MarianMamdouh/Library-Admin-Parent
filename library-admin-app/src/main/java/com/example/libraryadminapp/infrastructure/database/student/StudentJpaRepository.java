package com.example.libraryadminapp.infrastructure.database.student;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentName(@Param("studentName") String studentName);

    List<Student> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName);
}
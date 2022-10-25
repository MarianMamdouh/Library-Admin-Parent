package com.example.libraryadminapp.core.domain.student.repository;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);

    Optional<Student> findByStudentName(String studentName);

    Optional<Student> findByMobileNumber(String mobileNumber);

    List<Student> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName);

    Boolean existsByMobileNumber(String mobileNumber);

    Boolean existsByMobileNumberAndOTP(String mobileNumber, String otp);

    void deleteAll();

    Page<Student> findAll();

    Page<Student> findAllByCourseSlotsExists();
}

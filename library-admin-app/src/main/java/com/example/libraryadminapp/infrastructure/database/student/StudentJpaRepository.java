package com.example.libraryadminapp.infrastructure.database.student;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentName(@Param("studentName") String studentName);
    Optional<Student> findByMobileNumber(@Param("mobileNumber") String mobileNumber);

    Page<Student> findAll(Pageable pageable);

    Page<Student> findAllByCourseSlotsIsNotNull(Pageable pageable);

    List<Student> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName);
    Boolean existsByMobileNumber(String mobileNumber);
    Boolean existsByMobileNumberAndOtp(String mobileNumber, String OTP);

}

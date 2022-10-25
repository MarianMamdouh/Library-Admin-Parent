package com.example.libraryadminapp.infrastructure.database.student.impl;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.infrastructure.database.student.StudentJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentJpaRepository studentJpaRepository;

    @Override
    public Student save(final Student student) {

        return studentJpaRepository.save(student);
    }

    @Override
    public Optional<Student> findByStudentName(final String studentName) {

        return studentJpaRepository.findByStudentName(studentName);
    }

    @Override
    public Optional<Student> findByMobileNumber(final String mobileNumber) {

        return studentJpaRepository.findByMobileNumber(mobileNumber);
    }


    @Override
    public List<Student> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName) {

        return studentJpaRepository.findAllByAcademicYearAndFacultyName(academicYear, facultyName);
    }

    @Override
    @Transactional
    public Boolean existsByMobileNumber(final String mobileNumber) {

        return studentJpaRepository.existsByMobileNumber(mobileNumber);
    }

    @Override
    public Boolean existsByMobileNumberAndOTP(String mobileNumber, String otp) {

        return studentJpaRepository.existsByMobileNumberAndOtp(mobileNumber, otp);
    }

    @Override
    public void deleteAll() {

        studentJpaRepository.deleteAll();
    }

    @Override
    @Transactional
    public Page<Student> findAll() {

        return studentJpaRepository.findAll(Pageable.unpaged());
    }

    @Override
    @Transactional
    public Page<Student> findAllByCourseSlotsExists() {

        return studentJpaRepository.findAllByCourseSlotsIsNotNull(Pageable.unpaged());
    }

}

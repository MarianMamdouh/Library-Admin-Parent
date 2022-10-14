package com.example.libraryadminapp.infrastructure.database.student.impl;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.infrastructure.database.course.CourseJpaRepository;
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
    public Optional<Student> findByStudentName(String studentName) {

        return studentJpaRepository.findByStudentName(studentName);
    }


    @Override
    public List<Student> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName) {

        return studentJpaRepository.findAllByAcademicYearAndFacultyName(academicYear, facultyName);
    }
//
//    @Override
//    @Transactional
//    public Page<Course> findAll() {
//
//        return courseJpaRepository.findAll(Pageable.unpaged());
//    }
//
//    @Override
//    @Transactional
//    public void deleteByCourseName(String courseName) {
//
//        courseJpaRepository.deleteByCourseName(courseName);
//    }
}

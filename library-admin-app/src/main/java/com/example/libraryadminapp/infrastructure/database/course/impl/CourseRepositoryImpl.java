package com.example.libraryadminapp.infrastructure.database.course.impl;

import com.example.libraryadminapp.infrastructure.database.course.CourseJpaRepository;
import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.course.repository.CourseRepository;
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
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course save(Course course) {

        return courseJpaRepository.save(course);
    }

    @Override
    public Optional<Course> findByCourseName(String courseName) {

        return courseJpaRepository.findByCourseName(courseName);
    }

    @Override
    @Transactional
    public Page<Course> findAll() {

        return courseJpaRepository.findAll(Pageable.unpaged());
    }

    @Override
    @Transactional
    public void deleteByCourseName(String courseName) {

        courseJpaRepository.deleteByCourseName(courseName);
    }

    @Override
    public Page<Course> findAllByCourseNameOrSubjectNameOrProfessorName(String searchName) {

        return courseJpaRepository.findAllByCourseNameOrSubjectNameOrProfessorName(searchName, searchName, searchName, Pageable.unpaged());
    }

    @Override
    public List<Course> findAllByAcademicYearAndFacultyNameAndCourseNameOrSubjectNameOrProfessorName(final String academicYear, final String facultyName, final String searchTerm) {

        return courseJpaRepository.findAllByAcademicYear_YearAndFaculty_NameAndCourseNameOrSubjectNameOrProfessorName(academicYear, facultyName, searchTerm, searchTerm, searchTerm);
    }

    @Override
    public List<Course> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName) {

        return courseJpaRepository.findAllByAcademicYear_YearAndFaculty_Name(academicYear, facultyName);
    }
}

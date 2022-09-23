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
    public Page<Course> findAll() {

        return courseJpaRepository.findAll(Pageable.unpaged());
    }

    @Override
    @Transactional
    public void deleteByCourseName(String courseName) {

        courseJpaRepository.deleteByCourseName(courseName);
    }
}

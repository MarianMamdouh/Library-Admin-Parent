package com.example.libraryadminparent.infrastructure.database.course.impl;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.repository.CourseRepository;
import com.example.libraryadminparent.core.domain.professor.Professor;
import com.example.libraryadminparent.infrastructure.database.course.CourseJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course save(Course course) {

        return courseJpaRepository.save(course);
    }

    @Override
    public Page<Course> findAll() {

        return courseJpaRepository.findAll(Pageable.unpaged());
    }
}

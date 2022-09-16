package com.example.libraryadminparent.infrastructure.database.course.impl;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.repository.CourseRepository;
import com.example.libraryadminparent.infrastructure.database.course.CourseJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CourseRepositoryImpl implements CourseRepository {

    private final CourseJpaRepository courseJpaRepository;

    @Override
    public Course save(Course course) {

        return courseJpaRepository.save(course);
    }
}

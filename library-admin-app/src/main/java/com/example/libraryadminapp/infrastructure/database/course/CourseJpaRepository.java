package com.example.libraryadminapp.infrastructure.database.course;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseName(@Param("courseName") String courseName);

    void deleteByCourseName(@Param("courseName") String courseName);

}

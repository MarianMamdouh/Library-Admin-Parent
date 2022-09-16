package com.example.libraryadminparent.infrastructure.database.course;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseJpaRepository extends JpaRepository<Course, Long> {


}

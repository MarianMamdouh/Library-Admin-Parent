package com.example.libraryadminparent.core.domain.course.repository;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.service.CourseService;
import com.example.libraryadminparent.core.domain.professor.Professor;
import org.springframework.data.domain.Page;

public interface CourseRepository {

    Course save(Course course);

    Page<Course> findAll();

}

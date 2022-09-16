package com.example.libraryadminparent.core.domain.course.repository;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.service.CourseService;

public interface CourseRepository {

    Course save(Course course);
}

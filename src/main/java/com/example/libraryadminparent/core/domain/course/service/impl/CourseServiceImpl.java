package com.example.libraryadminparent.core.domain.course.service.impl;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.repository.CourseRepository;
import com.example.libraryadminparent.core.domain.course.request.CourseCreationRequestModel;
import com.example.libraryadminparent.core.domain.course.service.CourseService;
import com.example.libraryadminparent.core.domain.professor.Professor;
import com.example.libraryadminparent.core.domain.professor.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public void createCourse(final CourseCreationRequestModel courseCreationRequestModel) {

    final Professor professor = professorRepository.findByName(courseCreationRequestModel.getProfessorName());

    final Course course = buildCourseEntity(courseCreationRequestModel, professor);

    courseRepository.save(course);

    }

    private Course buildCourseEntity(final CourseCreationRequestModel courseCreationRequestModel, final Professor professor) {

        return Course.builder()
                .courseName(courseCreationRequestModel.getCourseName())
                .pricePerSemester(courseCreationRequestModel.getPricePerSemester())
                .pricePerMonth(courseCreationRequestModel.getPricePerMonth())
                .subjectName(courseCreationRequestModel.getSubjectName())
                .maxNumberOfBookings(courseCreationRequestModel.getMaxNumberOfBookings())
                .professor(professor)
                .build();
    }
}

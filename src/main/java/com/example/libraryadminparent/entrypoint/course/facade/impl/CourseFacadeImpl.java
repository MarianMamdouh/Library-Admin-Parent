package com.example.libraryadminparent.entrypoint.course.facade.impl;

import com.example.libraryadminparent.core.domain.course.request.CourseCreationRequestModel;
import com.example.libraryadminparent.core.domain.course.service.CourseService;
import com.example.libraryadminparent.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.course.facade.CourseFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    private final CourseService courseService;

    @Override
    public void createCourse(final CourseCreationRequestDTO courseCreationRequestDTO) {

        final CourseCreationRequestModel courseCreationRequestModel = buildCourseCreationRequestModel(courseCreationRequestDTO);
        courseService.createCourse(courseCreationRequestModel);
    }

    private CourseCreationRequestModel buildCourseCreationRequestModel(final CourseCreationRequestDTO courseCreationRequestDTO) {

       return CourseCreationRequestModel
                .builder()
                .courseName(courseCreationRequestDTO.getCourseName())
                .pricePerSemester(courseCreationRequestDTO.getPricePerSemester())
                .pricePerMonth(courseCreationRequestDTO.getPricePerMonth())
                .subjectName(courseCreationRequestDTO.getSubjectName())
                .professorName(courseCreationRequestDTO.getProfessorName())
                .maxNumberOfBookings(courseCreationRequestDTO.getMaxNumberOfBookings())
                .build();
    }
}

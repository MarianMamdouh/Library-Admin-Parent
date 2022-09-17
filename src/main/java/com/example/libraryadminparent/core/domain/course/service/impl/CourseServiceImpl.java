package com.example.libraryadminparent.core.domain.course.service.impl;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.repository.CourseRepository;
import com.example.libraryadminparent.core.domain.course.request.CourseCreationRequestModel;
import com.example.libraryadminparent.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminparent.core.domain.course.service.CourseService;
import com.example.libraryadminparent.core.domain.professor.Professor;
import com.example.libraryadminparent.core.domain.professor.repository.ProfessorRepository;
import com.example.libraryadminparent.core.domain.professor.response.ProfessorListResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Override
    public Page<CourseListResponseModel> getAllCourses() {

        Page<Course> courses = courseRepository.findAll();

        return courses.map(course ->
                CourseListResponseModel
                        .builder()
                        .courseName(course.getCourseName())
                        .pricePerSemester(course.getPricePerSemester())
                        .pricePerMonth(course.getPricePerMonth())
                        .subjectName(course.getSubjectName())
                        .maxNumberOfBookings(course.getMaxNumberOfBookings())
                        .professorName(course.getProfessor().getName())
                        .build());
    }

    private Course buildCourseEntity(final CourseCreationRequestModel courseCreationRequestModel, final Professor professor) {

        return Course
                .builder()
                .courseName(courseCreationRequestModel.getCourseName())
                .pricePerSemester(courseCreationRequestModel.getPricePerSemester())
                .pricePerMonth(courseCreationRequestModel.getPricePerMonth())
                .subjectName(courseCreationRequestModel.getSubjectName())
                .maxNumberOfBookings(courseCreationRequestModel.getMaxNumberOfBookings())
                .professor(professor)
                .build();
    }
}

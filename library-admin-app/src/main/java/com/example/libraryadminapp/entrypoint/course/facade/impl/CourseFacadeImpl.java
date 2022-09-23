package com.example.libraryadminapp.entrypoint.course.facade.impl;

import com.example.libraryadminapp.core.domain.course.request.CoursePaperRequestModel;
import com.example.libraryadminapp.entrypoint.course.controller.request.CoursePaperRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.facade.CourseFacade;
import com.example.libraryadminapp.core.domain.course.request.CourseRequestModel;
import com.example.libraryadminapp.core.domain.course.service.CourseService;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    private final CourseService courseService;

    @Override
    public void createCourse(final CourseCreationRequestDTO courseCreationRequestDTO) {

        final CourseRequestModel courseCreationRequestModel = buildCourseCreationRequestModel(courseCreationRequestDTO);
        courseService.createCourse(courseCreationRequestModel);
    }

    @Override
    public void updateCourse(final CourseUpdateRequestDTO courseUpdateRequestDTO) {

        final CourseRequestModel courseCreationRequestModel = buildCourseUpdateRequestModel(courseUpdateRequestDTO);
        courseService.updateCourse(courseCreationRequestModel);
    }

    @Override
    public void deleteCourse(final String courseName) {

        courseService.deleteCourse(courseName);
    }

    @Override
    public Page<CourseListResponseDTO> getAllCourses() {

        return courseService.getAllCourses().map(courseListResponseModel ->
                CourseListResponseDTO
                        .builder()
                        .courseName(courseListResponseModel.getCourseName())
                        .pricePerSemester(courseListResponseModel.getPricePerSemester())
                        .pricePerMonth(courseListResponseModel.getPricePerMonth())
                        .subjectName(courseListResponseModel.getSubjectName())
                        .maxNumberOfBookings(courseListResponseModel.getMaxNumberOfBookings())
                        .professorName(courseListResponseModel.getProfessorName())
                        .build());
    }

    private CourseRequestModel buildCourseCreationRequestModel(final CourseCreationRequestDTO courseCreationRequestDTO) {

       return CourseRequestModel
                .builder()
                .courseName(courseCreationRequestDTO.getCourseName())
                .pricePerSemester(courseCreationRequestDTO.getPricePerSemester())
                .pricePerMonth(courseCreationRequestDTO.getPricePerMonth())
                .subjectName(courseCreationRequestDTO.getSubjectName())
                .professorName(courseCreationRequestDTO.getProfessorName())
                .maxNumberOfBookings(courseCreationRequestDTO.getMaxNumberOfBookings())
                .coursePapers(courseCreationRequestDTO.getCoursePapers()
                        .stream()
                        .map(this::buildCoursePaperRequestModel)
                        .collect(Collectors.toList()))
                .build();
    }

    private CourseRequestModel buildCourseUpdateRequestModel(final CourseUpdateRequestDTO courseUpdateRequestDTO) {

        return CourseRequestModel
                .builder()
                .courseName(courseUpdateRequestDTO.getCourseName())
                .pricePerSemester(courseUpdateRequestDTO.getPricePerSemester())
                .pricePerMonth(courseUpdateRequestDTO.getPricePerMonth())
                .subjectName(courseUpdateRequestDTO.getSubjectName())
                .professorName(courseUpdateRequestDTO.getProfessorName())
                .maxNumberOfBookings(courseUpdateRequestDTO.getMaxNumberOfBookings())
                .coursePapers(courseUpdateRequestDTO.getCoursePapers()
                        .stream()
                        .map(this::buildCoursePaperRequestModel)
                        .collect(Collectors.toList()))
                .build();
    }

    private CoursePaperRequestModel buildCoursePaperRequestModel(final CoursePaperRequestDTO coursePaperRequestDTO) {

        return CoursePaperRequestModel
                .builder()
                .coursePaperName(coursePaperRequestDTO.getCoursePaperName())
                .numOfCopies(coursePaperRequestDTO.getNumOfCopies())
                .price(coursePaperRequestDTO.getPrice())
                .build();
    }
}

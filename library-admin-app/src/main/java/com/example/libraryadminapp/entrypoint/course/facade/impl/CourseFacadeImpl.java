package com.example.libraryadminapp.entrypoint.course.facade.impl;

import com.example.libraryadminapp.core.domain.course.request.CoursePaperRequestModel;
import com.example.libraryadminapp.core.domain.course.request.CourseSlotRequestModel;
import com.example.libraryadminapp.core.domain.course.response.CoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.course.response.CourseSlotResponseModel;
import com.example.libraryadminapp.entrypoint.course.controller.request.CoursePaperRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseSlotRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseSlotResponseDTO;
import com.example.libraryadminapp.entrypoint.course.facade.CourseFacade;
import com.example.libraryadminapp.core.domain.course.request.CourseRequestModel;
import com.example.libraryadminapp.core.domain.course.service.CourseService;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public void addCoursePaperToCourse(final String courseName, final CoursePaperRequestDTO coursePaperRequestDTO) {

        final CoursePaperRequestModel coursePaperRequestModel = buildCoursePaperRequestModel(coursePaperRequestDTO);
        courseService.addCoursePaperToCourse(courseName, coursePaperRequestModel);
    }

    @Override
    public void deleteCoursePaperFromCourse(final String courseName, final String coursePaperName) {

        courseService.deleteCoursePaperFromCourse(courseName, coursePaperName);
    }

    @Override
    public void deleteCourseSlotFromCourse(final String courseName, final Long courseSlotId) {

        courseService.deleteCourseSlotFromCourse(courseName, courseSlotId);

    }

    @Override
    public void addCourseSlotToCourse(String courseName, CourseSlotRequestDTO courseSlotRequestDTO) {

        final CourseSlotRequestModel courseSlotRequestModel = buildCourseSlotRequestModel(courseSlotRequestDTO);
        courseService.addCourseSlotToCourse(courseName, courseSlotRequestModel);
    }

    @Override
    public void deleteCourse(final String courseName) {

        courseService.deleteCourse(courseName);
    }

    @Override
    public Page<CourseListResponseDTO> getAllCourses() {

        return courseService.getAllCourses()
                .map(courseListResponseModel ->
                        CourseListResponseDTO
                            .builder()
                            .courseName(courseListResponseModel.getCourseName())
                            .pricePerSemester(courseListResponseModel.getPricePerSemester())
                            .pricePerMonth(courseListResponseModel.getPricePerMonth())
                            .subjectName(courseListResponseModel.getSubjectName())
                            .professorName(courseListResponseModel.getProfessorName())
                            .academicYear(courseListResponseModel.getAcademicYear())
                            .facultyName(courseListResponseModel.getFacultyName())
                            .courseSlots(courseListResponseModel.getCourseSlots().stream()
                                    .map(this::buildCourseSlotResponseModel)
                                    .collect(Collectors.toList()))
                            .coursePapers(courseListResponseModel.getCoursePapers().stream()
                                    .map(this::buildCoursePaperResponseModel)
                                    .collect(Collectors.toList()))
                            .build()
                );
    }

    @Override
    public List<CourseListResponseDTO> searchCoursesByMobileNumber(final String searchTerm, final String mobileName) {

        return courseService.searchCoursesByMobileNumber(searchTerm, mobileName)
                .stream()
                .map(courseListResponseModel ->
                        CourseListResponseDTO
                                .builder()
                                .courseName(courseListResponseModel.getCourseName())
                                .pricePerSemester(courseListResponseModel.getPricePerSemester())
                                .pricePerMonth(courseListResponseModel.getPricePerMonth())
                                .subjectName(courseListResponseModel.getSubjectName())
                                .professorName(courseListResponseModel.getProfessorName())
                                .academicYear(courseListResponseModel.getAcademicYear())
                                .facultyName(courseListResponseModel.getFacultyName())
                                .courseSlots(courseListResponseModel.getCourseSlots().stream()
                                        .map(this::buildCourseSlotResponseModel)
                                        .collect(Collectors.toList()))
                                .coursePapers(courseListResponseModel.getCoursePapers().stream()
                                        .map(this::buildCoursePaperResponseModel)
                                        .collect(Collectors.toList()))
                                .courseStatus(courseListResponseModel.getCourseStatus())
                                .build()
                ).collect(Collectors.toList());
    }

    @Override
    public Page<CourseListResponseDTO> searchCourses(final String searchName) {

        return courseService.searchCourses(searchName)
                .map(courseListResponseModel ->
                        CourseListResponseDTO
                                .builder()
                                .courseName(courseListResponseModel.getCourseName())
                                .pricePerSemester(courseListResponseModel.getPricePerSemester())
                                .pricePerMonth(courseListResponseModel.getPricePerMonth())
                                .subjectName(courseListResponseModel.getSubjectName())
                                .professorName(courseListResponseModel.getProfessorName())
                                .academicYear(courseListResponseModel.getAcademicYear())
                                .facultyName(courseListResponseModel.getFacultyName())
                                .courseSlots(courseListResponseModel.getCourseSlots().stream()
                                        .map(this::buildCourseSlotResponseModel)
                                        .collect(Collectors.toList()))
                                .coursePapers(courseListResponseModel.getCoursePapers().stream()
                                        .map(this::buildCoursePaperResponseModel)
                                        .collect(Collectors.toList()))
                                .build());
    }
    @Override
    public List<CourseListResponseDTO> getAllAvailableCoursesForStudent(String mobileNumber) {

        return courseService.getAllAvailableCoursesForStudent(mobileNumber)
                .stream()
                .map(courseListResponseModel ->
                        CourseListResponseDTO
                                .builder()
                                .courseName(courseListResponseModel.getCourseName())
                                .pricePerSemester(courseListResponseModel.getPricePerSemester())
                                .pricePerMonth(courseListResponseModel.getPricePerMonth())
                                .subjectName(courseListResponseModel.getSubjectName())
                                .professorName(courseListResponseModel.getProfessorName())
                                .academicYear(courseListResponseModel.getAcademicYear())
                                .facultyName(courseListResponseModel.getFacultyName())
                                .courseSlots(courseListResponseModel.getCourseSlots().stream()
                                        .map(this::buildCourseSlotResponseModel)
                                        .collect(Collectors.toList()))
                                .coursePapers(courseListResponseModel.getCoursePapers().stream()
                                        .map(this::buildCoursePaperResponseModel)
                                        .collect(Collectors.toList()))
                                .courseStatus(courseListResponseModel.getCourseStatus())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private CourseRequestModel buildCourseCreationRequestModel(final CourseCreationRequestDTO courseCreationRequestDTO) {

       return CourseRequestModel
                .builder()
                .courseName(courseCreationRequestDTO.getCourseName())
                .pricePerSemester(courseCreationRequestDTO.getPricePerSemester())
                .pricePerMonth(courseCreationRequestDTO.getPricePerMonth())
                .subjectName(courseCreationRequestDTO.getSubjectName())
                .professorName(courseCreationRequestDTO.getProfessorName())
                .academicYear(courseCreationRequestDTO.getAcademicYear())
                .facultyName(courseCreationRequestDTO.getFacultyName())
                .courseSlots(courseCreationRequestDTO.getCourseSlots()
                       .stream()
                       .map(this::buildCourseSlotRequestModel)
                       .collect(Collectors.toList()))
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
                .academicYear(courseUpdateRequestDTO.getAcademicYear())
                .facultyName(courseUpdateRequestDTO.getFacultyName())
                .build();
    }

    private CoursePaperRequestModel buildCoursePaperRequestModel(final CoursePaperRequestDTO coursePaperRequestDTO) {

        return CoursePaperRequestModel
                .builder()
                .coursePaperName(coursePaperRequestDTO.getCoursePaperName())
                .professorName(coursePaperRequestDTO.getProfessorName())
                .subjectName(coursePaperRequestDTO.getSubjectName())
                .facultyName(coursePaperRequestDTO.getFacultyName())
                .academicYear(coursePaperRequestDTO.getAcademicYear())
                .price(coursePaperRequestDTO.getPrice())
                .build();
    }

    private CourseSlotRequestModel buildCourseSlotRequestModel(final CourseSlotRequestDTO courseSlotRequestDTO) {

        return CourseSlotRequestModel
                .builder()
                .id(courseSlotRequestDTO.getId())
                .day(courseSlotRequestDTO.getDay())
                .startTime(courseSlotRequestDTO.getStartTime().toLocalTime())
                .endTime(courseSlotRequestDTO.getEndTime().toLocalTime())
                .maxNumberOfBookings(courseSlotRequestDTO.getMaxNumberOfBookings())
                .build();
    }

    private CoursePaperResponseDTO buildCoursePaperResponseModel(final CoursePaperResponseModel coursePaperResponseModel) {

        return CoursePaperResponseDTO
                .builder()
                .coursePaperName(coursePaperResponseModel.getCoursePaperName())
                .professorName(coursePaperResponseModel.getProfessorName())
                .subjectName(coursePaperResponseModel.getSubjectName())
                .academicYear(coursePaperResponseModel.getAcademicYear())
                .facultyName(coursePaperResponseModel.getFacultyName())
                .price(coursePaperResponseModel.getPrice())
                .academicYear(coursePaperResponseModel.getAcademicYear())
                .build();
    }

    private CourseSlotResponseDTO buildCourseSlotResponseModel(final CourseSlotResponseModel courseSlotResponseModel) {

        return CourseSlotResponseDTO
                .builder()
                .id(courseSlotResponseModel.getId())
                .day(courseSlotResponseModel.getDay())
                .startTime(courseSlotResponseModel.getStartTime())
                .endTime(courseSlotResponseModel.getEndTime())
                .maxNumberOfBookings(courseSlotResponseModel.getMaxNumberOfBookings())
                .currentNumberOfBookings(courseSlotResponseModel.getCurrentNumberOfBookings())
                .build();
    }
}

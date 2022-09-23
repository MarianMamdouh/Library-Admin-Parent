package com.example.libraryadminapp.core.domain.course.service.impl;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.course.repository.CourseRepository;
import com.example.libraryadminapp.core.domain.course.request.CourseRequestModel;
import com.example.libraryadminapp.core.domain.course.request.CoursePaperRequestModel;
import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.course.service.CourseService;
import com.example.libraryadminapp.core.domain.coursepaper.CoursePaper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CoursePaperRepository coursePaperRepository;

    public void createCourse(final CourseRequestModel courseRequestModel) {

        final Course course = buildCourseEntity(courseRequestModel);

        courseRepository.save(course);

        final List<CoursePaper> coursePaper = buildCoursePaperEntities(courseRequestModel.getCoursePapers(), course);

        coursePaperRepository.saveAll(coursePaper);
    }

    public void updateCourse(final CourseRequestModel courseRequestModel) {

        final Optional<Course> courseOptional = courseRepository.findByCourseName(courseRequestModel.getCourseName());

        courseOptional.ifPresent(course -> {

            updateCourse(courseRequestModel, course);
            updateCoursePapers(courseRequestModel, course);
        });
    }

    @Override
    @Transactional
    public void deleteCourse(String courseName) {

        final Optional<Course> courseOptional = courseRepository.findByCourseName(courseName);

        courseOptional.ifPresentOrElse(course -> {

            final List<CoursePaper> coursePapers = coursePaperRepository.findAllByCourseId(course.getId());

            coursePapers.forEach(

                    coursePaper -> coursePaperRepository.deleteByCoursePaperName(coursePaper.getCoursePaperName())
            );

            courseRepository.deleteByCourseName(courseName);


        }, () ->{});
    }

    private Course updateCourse(final CourseRequestModel courseRequestModel, final Course course) {

        course.setProfessorName(courseRequestModel.getProfessorName());
        course.setSubjectName(courseRequestModel.getSubjectName());
        course.setMaxNumberOfBookings(courseRequestModel.getMaxNumberOfBookings());
        course.setPricePerMonth(courseRequestModel.getPricePerMonth());
        course.setPricePerSemester(courseRequestModel.getPricePerSemester());

        return courseRepository.save(course);
    }

    private void updateCoursePapers(final CourseRequestModel courseRequestModel, final Course course) {

        courseRequestModel.getCoursePapers().forEach(coursePaper -> {

            final Optional<CoursePaper> coursePaperOptional = coursePaperRepository.findByName(coursePaper.getCoursePaperName());

            coursePaperOptional.ifPresentOrElse(existingCoursePaper -> {

                existingCoursePaper.setProfessorName(courseRequestModel.getProfessorName());
                existingCoursePaper.setSubjectName(courseRequestModel.getSubjectName());
                existingCoursePaper.setNumOfCopies(coursePaper.getNumOfCopies());
                existingCoursePaper.setPrice(coursePaper.getPrice());
                existingCoursePaper.setCourse(course);

                coursePaperRepository.save(existingCoursePaper);
            }, () -> {

                final CoursePaper coursePaper1 = CoursePaper.builder()
                        .professorName(courseRequestModel.getProfessorName())
                        .subjectName(courseRequestModel.getSubjectName())
                        .coursePaperName(coursePaper.getCoursePaperName())
                        .price(coursePaper.getPrice())
                        .numOfCopies(coursePaper.getNumOfCopies())
                        .course(course)
                        .build();

                coursePaperRepository.save(coursePaper1);
            });
        });
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
                        .build());
    }

    private Course buildCourseEntity(final CourseRequestModel courseCreationRequestModel) {

        return Course
                .builder()
                .courseName(courseCreationRequestModel.getCourseName())
                .pricePerSemester(courseCreationRequestModel.getPricePerSemester())
                .pricePerMonth(courseCreationRequestModel.getPricePerMonth())
                .subjectName(courseCreationRequestModel.getSubjectName())
                .maxNumberOfBookings(courseCreationRequestModel.getMaxNumberOfBookings())
                .professorName(courseCreationRequestModel.getProfessorName())
                .build();
    }

    private List<CoursePaper> buildCoursePaperEntities(final List<CoursePaperRequestModel> coursePaperRequestModels, final Course course) {

        return coursePaperRequestModels
                .stream()
                .map(coursePaperRequestModel -> buildCoursePaperEntity(course, coursePaperRequestModel))
                .collect(Collectors.toList());
    }

    private CoursePaper buildCoursePaperEntity(final Course course, final CoursePaperRequestModel coursePaperRequestModel) {

        return CoursePaper
                .builder()
                .subjectName(course.getSubjectName())
                .professorName(course.getProfessorName())
                .coursePaperName(coursePaperRequestModel.getCoursePaperName())
                .price(coursePaperRequestModel.getPrice())
                .numOfCopies(coursePaperRequestModel.getNumOfCopies())
                .course(course)
                .build();
    }


}

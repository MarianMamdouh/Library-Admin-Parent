package com.example.libraryadminapp.core.domain.course.service.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.academicyear.repository.AcademicYearRepository;
import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.course.repository.CourseRepository;
import com.example.libraryadminapp.core.domain.course.request.CoursePaperRequestModel;
import com.example.libraryadminapp.core.domain.course.request.CourseRequestModel;
import com.example.libraryadminapp.core.domain.course.request.CourseSlotRequestModel;
import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminapp.core.domain.course.response.CoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.course.response.CourseSlotResponseModel;
import com.example.libraryadminapp.core.domain.course.service.CourseService;
import com.example.libraryadminapp.core.domain.course.utils.WeekDay;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.courseslot.repository.CourseSlotRepository;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import com.example.libraryadminapp.core.domain.faculty.repository.FacultyRepository;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CoursePaperRepository coursePaperRepository;
    private final CourseSlotRepository courseSlotRepository;
    private final AcademicYearRepository academicYearRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public void createCourse(final CourseRequestModel courseRequestModel) {

        final Optional<AcademicYear> academicYear = academicYearRepository.findByYear(courseRequestModel.getAcademicYear());
        final Optional<Faculty> faculty = facultyRepository.findByFacultyName(courseRequestModel.getFacultyName());

        final Course course = buildCourseEntity(courseRequestModel, academicYear.get(), faculty.get());

        courseRepository.save(course);

        final List<CourseSlot> courseSlots = buildCourseSlotEntities(courseRequestModel.getCourseSlots(), course);

        courseSlotRepository.saveAll(courseSlots);

        createOrUpdateCoursePaper(courseRequestModel.getCoursePapers(), course, academicYear.get(), faculty.get());
    }

    public void updateCourse(final CourseRequestModel courseRequestModel) {

        final Optional<Course> courseOptional = courseRepository.findByCourseName(courseRequestModel.getCourseName());

        courseOptional.ifPresent(course -> {

            //  clearCourseSlots(courseRequestModel, course);
            updateCourse(courseRequestModel, course);
//            updateCoursePapers(courseRequestModel, course);
//            updateCourseSlots(courseRequestModel, course);
        });
    }

    @Override
    public void addCoursePaperToCourse(final String courseName, final CoursePaperRequestModel coursePaperRequestModel) {

        final Optional<Course> courseOptional = courseRepository.findByCourseName(courseName);

        courseOptional.ifPresent(course -> {

            //  clearCourseSlots(courseRequestModel, course);
//            updateCourse(courseRequestModel, course);
            updateCoursePapers(coursePaperRequestModel, course);
//            updateCourseSlots(courseRequestModel, course);
        });

    }

    @Override
    public void deleteCoursePaperFromCourse(final String courseName, final String coursePaperName) {

        final CoursePaper coursePaperOptional = coursePaperRepository.findByName(coursePaperName).get();

        coursePaperOptional.setCourse(null);

        coursePaperRepository.save(coursePaperOptional);
//        coursePaperRepository.deleteByCoursePaperName(coursePaperName);
    }

    @Override
    public void deleteCourseSlotFromCourse(String courseName, Long courseSlotId) {

        courseSlotRepository.deleteById(courseSlotId);


    }

    @Override
    public void addCourseSlotToCourse(String courseName, CourseSlotRequestModel courseSlotRequestModel) {


        final Optional<Course> courseOptional = courseRepository.findByCourseName(courseName);

        courseOptional.ifPresent(course -> {

            //  clearCourseSlots(courseRequestModel, course);
//            updateCourse(courseRequestModel, course);
//            updateCoursePapers(coursePaperRequestModel, course);
            updateCourseSlots(courseSlotRequestModel, course);
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

            final List<CourseSlot> courseSlots = courseSlotRepository.findAllByCourseId(course.getId());

            courseSlots.forEach(

                    courseSlot -> courseSlotRepository.deleteById(courseSlot.getId())
            );

            courseRepository.deleteByCourseName(courseName);


        }, () -> {
        });
    }

    private Course updateCourse(final CourseRequestModel courseRequestModel, final Course course) {

        course.setProfessorName(courseRequestModel.getProfessorName());
        course.setSubjectName(courseRequestModel.getSubjectName());
        course.setPricePerMonth(courseRequestModel.getPricePerMonth());
        course.setPricePerSemester(courseRequestModel.getPricePerSemester());
        course.setCourseSlots(Lists.emptyList());
        final Optional<AcademicYear> academicYear = academicYearRepository.findByYear(courseRequestModel.getAcademicYear());
        final Optional<Faculty> faculty = facultyRepository.findByFacultyName(courseRequestModel.getFacultyName());

        course.setAcademicYear(academicYear.get());
        course.setFaculty(faculty.get());
        return courseRepository.save(course);
    }

//    private void clearCourseSlots(final CourseRequestModel courseRequestModel, final Course course) {
//
//        course.getCourseSlots().forEach(courseSlot -> {
//
//            final Optional<CoursePaper> coursePaperOptional = courseSlotRepository.(coursePaper.getCoursePaperName());
//
//            coursePaperOptional.ifPresentOrElse(existingCoursePaper -> {
//
//                existingCoursePaper.setProfessorName(courseRequestModel.getProfessorName());
//                existingCoursePaper.setSubjectName(courseRequestModel.getSubjectName());
//                existingCoursePaper.setPrice(coursePaper.getPrice());
//                existingCoursePaper.setCourse(course);
//
//                coursePaperRepository.save(existingCoursePaper);
//            }, () -> {
//
//                final CoursePaper coursePaper1 = CoursePaper.builder()
//                        .professorName(courseRequestModel.getProfessorName())
//                        .subjectName(courseRequestModel.getSubjectName())
//                        .coursePaperName(coursePaper.getCoursePaperName())
//                        .price(coursePaper.getPrice())
//                        .course(course)
//                        .build();
//
//                coursePaperRepository.save(coursePaper1);
//
//            });
//        });
//    }

    private void updateCoursePapers(final CoursePaperRequestModel coursePaperRequestModel, final Course course) {

        final Optional<CoursePaper> coursePaperOptional = coursePaperRepository.findByName(coursePaperRequestModel.getCoursePaperName());

        coursePaperOptional.ifPresentOrElse(existingCoursePaper -> {
            // todo throw an error that you can't a course paper name that already exists

            existingCoursePaper.setProfessorName(coursePaperRequestModel.getProfessorName());
            existingCoursePaper.setSubjectName(coursePaperRequestModel.getSubjectName());
            existingCoursePaper.setPrice(coursePaperRequestModel.getPrice());
            existingCoursePaper.setCourse(course);

            coursePaperRepository.save(existingCoursePaper);
        }, () -> {

            final Optional<AcademicYear> academicYear = academicYearRepository.findByYear(coursePaperRequestModel.getAcademicYear());
            final Optional<Faculty> faculty = facultyRepository.findByFacultyName(coursePaperRequestModel.getFacultyName());

            final CoursePaper coursePaper1 = CoursePaper.builder()
                    .professorName(coursePaperRequestModel.getProfessorName())
                    .subjectName(coursePaperRequestModel.getSubjectName())
                    .coursePaperName(coursePaperRequestModel.getCoursePaperName())
                    .price(coursePaperRequestModel.getPrice())
                    .faculty(faculty.get())
                    .academicYear(academicYear.get())
                    .course(course)
                    .build();

            coursePaperRepository.save(coursePaper1);

        });
    }

    private void updateCourseSlots(final CourseSlotRequestModel courseSlotRequestModel, final Course course) {

        final CourseSlot courseSlot = CourseSlot.builder()
                .day(WeekDay.valueOf(courseSlotRequestModel.getDay()))
                .startTime(LocalDateTime.of(LocalDate.now(), courseSlotRequestModel.getStartTime()))
                .endTime(LocalDateTime.of(LocalDate.now(), courseSlotRequestModel.getEndTime()))
                .maximumNumberOfBookings(courseSlotRequestModel.getMaxNumberOfBookings())
                .currentNumberOfBookings(0)
                .course(course)
                .build();

        courseSlotRepository.save(courseSlot);
    }

    @Override
    @Transactional
    public Page<CourseListResponseModel> getAllCourses() {

        Page<Course> courses = courseRepository.findAll();

        return courses.map(course ->
                CourseListResponseModel
                        .builder()
                        .courseName(course.getCourseName())
                        .professorName(course.getProfessorName())
                        .subjectName(course.getSubjectName())
                        .pricePerSemester(course.getPricePerSemester())
                        .pricePerMonth(course.getPricePerMonth())
                        .academicYear(course.getAcademicYear().getYear())
                        .facultyName(course.getFaculty().getName())
                        .courseSlots(getCourseSLotsForCourse(course))
                        .coursePapers(getCoursePapersForCourse(course))
                        .build());
    }

    @Override
    public List<CourseListResponseModel> getAllAvailableCoursesForStudent(final String studentName) {

        final Optional<Student> student = studentRepository.findByStudentName(studentName);

        final String academicYear = student.get().getAcademicYear();
        final String facultyName = student.get().getFacultyName();

        List<Course> courses = courseRepository.findAllByAcademicYearAndFacultyName(academicYear, facultyName);

        return courses.stream().map(course ->
                        CourseListResponseModel
                                .builder()
                                .courseName(course.getCourseName())
                                .professorName(course.getProfessorName())
                                .subjectName(course.getSubjectName())
                                .pricePerSemester(course.getPricePerSemester())
                                .pricePerMonth(course.getPricePerMonth())
                                .academicYear(course.getAcademicYear().getYear())
                                .facultyName(course.getFaculty().getName())
                                .courseSlots(getCourseSLotsForCourse(course))
                                .coursePapers(getCoursePapersForCourse(course))
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseListResponseModel> searchCourses(String searchName) {

        List<Course> courses = courseRepository.findAllByCourseNameOrSubjectNameOrProfessorName(searchName);

        return courses.stream()
                .map(course ->
                        CourseListResponseModel
                                .builder()
                                .courseName(course.getCourseName())
                                .professorName(course.getProfessorName())
                                .subjectName(course.getSubjectName())
                                .pricePerSemester(course.getPricePerSemester())
                                .pricePerMonth(course.getPricePerMonth())
                                .facultyName(course.getFaculty().getName())
                                .academicYear(course.getAcademicYear().getYear())
                                .courseSlots(getCourseSLotsForCourse(course))
                                .coursePapers(getCoursePapersForCourse(course))
                                .build())
                .collect(Collectors.toList());
    }

    private List<CoursePaperResponseModel> getCoursePapersForCourse(final Course course) {

        final List<CoursePaper> coursePapers = coursePaperRepository.findAllByCourseId(course.getId());

        return coursePapers.stream()
                .map(this::buildCoursePaperResponseModel)
                .collect(Collectors.toList());

    }

    private CoursePaperResponseModel buildCoursePaperResponseModel(final CoursePaper coursePaper) {

        return CoursePaperResponseModel
                .builder()
                .coursePaperName(coursePaper.getCoursePaperName())
                .professorName(coursePaper.getProfessorName())
                .subjectName(coursePaper.getSubjectName())
                .academicYear(coursePaper.getAcademicYear().getYear())
                .facultyName(coursePaper.getFaculty().getName())
                .price(coursePaper.getPrice())
                .build();
    }

    private List<CourseSlotResponseModel> getCourseSLotsForCourse(final Course course) {

        final List<CourseSlot> coursePapers = courseSlotRepository.findAllByCourseId(course.getId());

        return coursePapers.stream()
                .map(this::buildCourseSlotResponseModel)
                .collect(Collectors.toList());
    }

    private CourseSlotResponseModel buildCourseSlotResponseModel(final CourseSlot courseSlot) {

        return CourseSlotResponseModel
                .builder()
                .id(courseSlot.getId())
                .day(courseSlot.getDay().name())
                .startTime(courseSlot.getStartTime().toLocalTime())
                .endTime(courseSlot.getEndTime().toLocalTime())
                .maxNumberOfBookings(courseSlot.getMaximumNumberOfBookings())
                .build();
    }

    private Course buildCourseEntity(final CourseRequestModel courseCreationRequestModel,
                                     final AcademicYear academicYear,
                                     final Faculty faculty) {

        return Course
                .builder()
                .courseName(courseCreationRequestModel.getCourseName())
                .pricePerSemester(courseCreationRequestModel.getPricePerSemester())
                .pricePerMonth(courseCreationRequestModel.getPricePerMonth())
                .subjectName(courseCreationRequestModel.getSubjectName())
                .professorName(courseCreationRequestModel.getProfessorName())
                .academicYear(academicYear)
                .faculty(faculty)
                .build();
    }

    private void createOrUpdateCoursePaper(final List<CoursePaperRequestModel> coursePaperRequestModels, final Course course,
                                           final AcademicYear academicYear,
                                           final Faculty faculty) {
        coursePaperRequestModels.forEach(
                coursePaperRequestModel -> {

                    Optional<CoursePaper> coursePaper = coursePaperRepository.findByName(coursePaperRequestModel.getCoursePaperName());
                    coursePaper.ifPresentOrElse(coursePaper1 -> {

                        coursePaper1.setCourse(course);
                        coursePaperRepository.save(coursePaper1);

                    }, () -> {
                        CoursePaper coursePaper2 = buildCoursePaperEntity(course, coursePaperRequestModel, academicYear, faculty);
                        coursePaperRepository.save(coursePaper2);

                    });


                });
//
//
//        return coursePaperRequestModels
//                .stream()
//                .map(coursePaperRequestModel -> buildCoursePaperEntity(course, coursePaperRequestModel, academicYear, faculty))
//                .collect(Collectors.toList());
    }

    private CoursePaper buildCoursePaperEntity(final Course course, final CoursePaperRequestModel coursePaperRequestModel,
                                               final AcademicYear academicYear,
                                               final Faculty faculty) {

        return CoursePaper
                .builder()
                .subjectName(course.getSubjectName())
                .professorName(course.getProfessorName())
                .coursePaperName(coursePaperRequestModel.getCoursePaperName())
                .price(coursePaperRequestModel.getPrice())
                .academicYear(academicYear)
                .faculty(faculty)
                .course(course)
                .build();
    }

    private List<CourseSlot> buildCourseSlotEntities(final List<CourseSlotRequestModel> courseSlotRequestModels, final Course course) {

        return courseSlotRequestModels
                .stream()
                .map(courseSlotRequestModel -> buildCourseSlotEntity(courseSlotRequestModel, course))
                .collect(Collectors.toList());
    }

    private CourseSlot buildCourseSlotEntity(final CourseSlotRequestModel courseSlotRequestModel, final Course course) {

        return CourseSlot
                .builder()
                .day(WeekDay.valueOf(courseSlotRequestModel.getDay()))
                .startTime(LocalDateTime.of(LocalDate.now(), courseSlotRequestModel.getStartTime()))
                .endTime(LocalDateTime.of(LocalDate.now(), courseSlotRequestModel.getEndTime()))
                .maximumNumberOfBookings(courseSlotRequestModel.getMaxNumberOfBookings())
                .currentNumberOfBookings(0)
                .course(course)
                .build();
    }
}
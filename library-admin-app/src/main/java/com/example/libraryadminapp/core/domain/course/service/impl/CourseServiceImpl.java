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
import com.example.libraryadminapp.core.domain.course.utils.CourseStatus;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        final AcademicYear academicYear = findByYear(courseRequestModel.getAcademicYear());

        final Faculty faculty = findByFacultyName(courseRequestModel.getFacultyName());

        final Course course = buildCourseEntity(courseRequestModel, academicYear, faculty);

        courseRepository.save(course);

        final List<Student> students = studentRepository.findAllByAcademicYearAndFacultyName(academicYear.getYear(), faculty.getName());

        final List<CourseSlot> courseSlots = buildCourseSlotEntities(courseRequestModel.getCourseSlots(), course);

        courseSlotRepository.saveAll(courseSlots);

        createOrUpdateCoursePaper(courseRequestModel.getCoursePapers(), course, academicYear, faculty);

        students.forEach(student -> {
//            try {
            final String notificationBody = "Course " + course.getCourseName() + " has been added in our library!";
//                firebaseMessagingService.sendNotification(student.getFcmToken(), "New Course Added", notificationBody);
            student.getNotification().add(notificationBody);

            studentRepository.save(student);

//            } catch (FirebaseMessagingException e) {
//
////                throw new RuntimeException(e);
//            }

        });

    }

    public void updateCourse(final CourseRequestModel courseRequestModel) {

        final Course course = findByCourseName(courseRequestModel.getCourseName());

        updateCourse(courseRequestModel, course);
    }

    @Override
    public void addCoursePaperToCourse(final String courseName, final CoursePaperRequestModel coursePaperRequestModel) {

        final Course course = findByCourseName(courseName);

        addCoursePaperToCourse(coursePaperRequestModel, course);
    }

    @Override
    public void deleteCoursePaperFromCourse(final String courseName, final String coursePaperName) {

        final CoursePaper coursePaper = coursePaperRepository.findByName(coursePaperName)
                .orElseThrow(() -> new IllegalArgumentException("Course Paper " + coursePaperName + " can't be found"));

        final Course course = courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("Course " + courseName + " can't be found"));

        course.getCoursePapers().remove(coursePaper);

        courseRepository.save(course);
    }

    @Override
    public void deleteCourseSlotFromCourse(final String courseName, final Long courseSlotId) {

        courseSlotRepository.deleteById(courseSlotId);
    }

    @Override
    public void addCourseSlotToCourse(final String courseName, final CourseSlotRequestModel courseSlotRequestModel) {

        final Course course = findByCourseName(courseName);
        updateCourseSlots(courseSlotRequestModel, course);
    }

    @Override
    @Transactional
    public void deleteCourse(final String courseName) {

        final Course course = findByCourseName(courseName);

//        final List<CoursePaper> coursePapers = coursePaperRepository.findAllByCourseId(course.getId());
//
//        coursePapers.forEach(
//
//                coursePaper -> coursePaperRepository.deleteByCoursePaperName(coursePaper.getCoursePaperName())
//        );

        final List<CourseSlot> courseSlots = courseSlotRepository.findAllByCourseId(course.getId());

        courseSlots.forEach(

                courseSlot -> courseSlotRepository.deleteById(courseSlot.getId())
        );

        courseRepository.deleteByCourseName(courseName);
    }

    private Course updateCourse(final CourseRequestModel courseRequestModel, final Course course) {

        course.setProfessorName(courseRequestModel.getProfessorName());
        course.setSubjectName(courseRequestModel.getSubjectName());
        course.setPricePerMonth(courseRequestModel.getPricePerMonth());
        course.setPricePerSemester(courseRequestModel.getPricePerSemester());
        course.setCourseSlots(Lists.emptyList());

        final AcademicYear academicYear = findByYear(courseRequestModel.getAcademicYear());
        final Faculty faculty = findByFacultyName(courseRequestModel.getFacultyName());

        course.setAcademicYear(academicYear);
        course.setFaculty(faculty);

        return courseRepository.save(course);
    }

    private void addCoursePaperToCourse(final CoursePaperRequestModel coursePaperRequestModel, final Course course) {

        final Optional<CoursePaper> coursePaperOptional = coursePaperRepository.findByName(coursePaperRequestModel.getCoursePaperName());/**/

        coursePaperOptional.ifPresentOrElse(existingCoursePaper -> {

            if (course.getCoursePapers().contains(existingCoursePaper)) {

                throw new IllegalArgumentException("Course Paper " + coursePaperRequestModel.getCoursePaperName() + " already assigned to course " + course.getCourseName());
            } else {

                course.getCoursePapers().add(existingCoursePaper);
                courseRepository.save(course);
            }
        }, () -> {

            final AcademicYear academicYear = findByYear(coursePaperRequestModel.getAcademicYear());
            final Faculty faculty = findByFacultyName(coursePaperRequestModel.getFacultyName());

            final CoursePaper coursePaper = CoursePaper.builder()
                    .professorName(coursePaperRequestModel.getProfessorName())
                    .subjectName(coursePaperRequestModel.getSubjectName())
                    .coursePaperName(coursePaperRequestModel.getCoursePaperName())
                    .price(coursePaperRequestModel.getPrice())
                    .faculty(faculty)
                    .academicYear(academicYear)
//                    .course(course)
                    .build();

            coursePaperRepository.save(coursePaper);
            course.getCoursePapers().add(coursePaper);
            courseRepository.save(course);

        });
    }

    private void updateCourseSlots(final CourseSlotRequestModel courseSlotRequestModel, final Course course) {

        final CourseSlot courseSlot = CourseSlot.builder()
                .day(courseSlotRequestModel.getDay())
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
    public List<CourseListResponseModel> getAllAvailableCoursesForStudent(final String mobileNumber) {

        final Student student = studentRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Student with mobile number " + mobileNumber + " can't be found"));

        final String academicYear = student.getAcademicYear();
        final String facultyName = student.getFacultyName();

        List<Course> courses = courseRepository.findAllByAcademicYearAndFacultyName(academicYear, facultyName);

        return courses.stream()
                .map(course ->
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
                                .courseStatus(getCourseStatus(course, mobileNumber))
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseListResponseModel> searchCoursesByMobileNumber(final String searchName, final String mobileNumber) {

        final Student student = studentRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Student with mobile number " + mobileNumber + " can't be found"));

        final String academicYear = student.getAcademicYear();
        final String facultyName = student.getFacultyName();

        List<Course> courses = courseRepository.findAllByAcademicYearAndFacultyNameAndCourseNameOrSubjectNameOrProfessorName(academicYear, facultyName, searchName);

        return courses.stream().map(course ->
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
                                .courseStatus(getCourseStatus(course, mobileNumber))
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Page<CourseListResponseModel> searchCourses(final String searchTerm) {

        Page<Course> courses = courseRepository.findAllByCourseNameOrSubjectNameOrProfessorName(searchTerm);

        return courses.map(course ->
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
                        .build()
        );
    }

    private String getCourseStatus(final Course course, final String mobileNumber) {

        final Student student = studentRepository.findByMobileNumber(mobileNumber).get();

        final List<CourseSlot> courseSlots = student.getCourseSlots();

        final List<CourseSlot> courseCourseSlots = courseSlotRepository.findAllByCourseId(course.getId());

        if (courseCourseSlots.stream()
                .anyMatch(courseSlots::contains)) {

            return CourseStatus.ALREADY_BOOKED.toString();
        }

        if (course.getCourseSlots()
                .stream()
                .allMatch(courseSlot -> Objects.equals(courseSlot.getCurrentNumberOfBookings(), courseSlot.getMaximumNumberOfBookings()))) {

            return CourseStatus.FULLY_BOOKED.toString();
        }

        return CourseStatus.AVAILABLE_TO_BOOK.toString();
    }

    private List<CoursePaperResponseModel> getCoursePapersForCourse(final Course course) {

        final List<CoursePaper> coursePapers = course.getCoursePapers();

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
                .day(courseSlot.getDay())
                .startTime(courseSlot.getStartTime().toLocalTime())
                .endTime(courseSlot.getEndTime().toLocalTime())
                .maxNumberOfBookings(courseSlot.getMaximumNumberOfBookings())
                .currentNumberOfBookings(courseSlot.getCurrentNumberOfBookings())
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
                .courseSlots(new ArrayList<>())
                .coursePapers(new ArrayList<>())
                .build();
    }

    private void createOrUpdateCoursePaper(final List<CoursePaperRequestModel> coursePaperRequestModels, final Course course,
                                           final AcademicYear academicYear,
                                           final Faculty faculty) {
        coursePaperRequestModels.forEach(
                coursePaperRequestModel -> {

                    coursePaperRepository.findByName(coursePaperRequestModel.getCoursePaperName())
                            .ifPresentOrElse(coursePaper1 -> {

                                course.getCoursePapers().add(coursePaper1);
                                courseRepository.save(course);
                            }, () -> {

                                CoursePaper coursePaper2 = buildCoursePaperEntity(course, coursePaperRequestModel, academicYear, faculty);
                                course.getCoursePapers().add(coursePaper2);
                                courseRepository.save(course);
                            });
                });
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
                .day(courseSlotRequestModel.getDay())
                .startTime(LocalDateTime.of(LocalDate.now(), courseSlotRequestModel.getStartTime()))
                .endTime(LocalDateTime.of(LocalDate.now(), courseSlotRequestModel.getEndTime()))
                .maximumNumberOfBookings(courseSlotRequestModel.getMaxNumberOfBookings())
                .currentNumberOfBookings(0)
                .course(course)
                .build();
    }

    private AcademicYear findByYear(final String year) {

        return academicYearRepository.findByYear(year)
                .orElseThrow(() -> new IllegalArgumentException("Year " + year + " can't be found"));
    }

    private Faculty findByFacultyName(final String facultyName) {

        return facultyRepository.findByFacultyName(facultyName)
                .orElseThrow(() -> new IllegalArgumentException("Faculty " + facultyName + " can't be found"));
    }

    private Course findByCourseName(final String courseName) {

        return courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("Course " + courseName + " can't be found"));
    }
}

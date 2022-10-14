package com.example.libraryadminapp.core.domain.student.service.impl;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.course.repository.CourseRepository;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.courseslot.repository.CourseSlotRepository;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCourseResponseModel;
import com.example.libraryadminapp.core.domain.student.service.StudentService;
import com.example.libraryadminapp.core.domain.student.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CoursePaperRepository coursePaperRepository;

    private final CourseSlotRepository courseSlotRepository;


    public void createStudent(final StudentCreationRequestModel studentCreationRequestModel) {

        final Student student = buildStudentEntity(studentCreationRequestModel);

        studentRepository.save(student);

    }

    @Override
    public void assignCourseToStudent(final String courseName, final String studentName, final Long courseSlotId) {

        final Optional<Course> course = courseRepository.findByCourseName(courseName);

        // should i make a check if number of current bookings = max

        final Student student = studentRepository.findByStudentName(studentName).get();

        List<CourseSlot> courseSlots = courseSlotRepository.findAllByCourseId(course.get().getId());


        if (student.getCourseSlots()
                .stream()
                .map(CourseSlot::getId)
                .anyMatch(courseSlotId::equals)) {

            //throw an error that student is already assigned to this course
            return ;
        }

        final CourseSlot courseSlot = courseSlots
                .stream()
                .filter(courseSlot1 -> courseSlot1.getId().equals(courseSlotId))
                .findFirst().get();

        student.getCourseSlots().add(courseSlot);
        studentRepository.save(student);

        courseSlot.setCurrentNumberOfBookings(courseSlot.getCurrentNumberOfBookings() + 1);
        courseSlotRepository.save(courseSlot);
    }

    @Override
    public void assignCoursePaperToStudent(final String coursePaperName, final String studentName) {

        final Optional<CoursePaper> coursePaper = coursePaperRepository.findByName(coursePaperName);

        final Student student = studentRepository.findByStudentName(studentName).get();

        if (student.getCoursePapers()
                .stream()
                .map(CoursePaper::getCoursePaperName)
                .anyMatch(coursePaperName::equals)) {

            // throw an error that student is already assigned to this course.
            return;
        }

        student.getCoursePapers().add(coursePaper.get());
        studentRepository.save(student);
    }

    @Override
    public List<StudentCourseResponseModel> getAllCoursesBookings(final String studentName) {

        final Student student = studentRepository.findByStudentName(studentName).get();

        final List<CourseSlot> courseSlots = student.getCourseSlots();

        final List<Course> courses = courseSlots
                .stream()
                .map(CourseSlot::getCourse)
                .collect(Collectors.toList());

        return courses.stream()
                .map(this::buildStudentCourseResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentCoursePaperResponseModel> getAllCoursePapersBookings(final String studentName) {

        final Student student = studentRepository.findByStudentName(studentName).get();

        final List<CoursePaper> coursePapers = student.getCoursePapers();

        return coursePapers.stream()
                .map(this::buildStudentCoursePaperResponseModel)
                .collect(Collectors.toList());
    }

    private StudentCourseResponseModel buildStudentCourseResponseModel(final Course course) {

        return StudentCourseResponseModel
                .builder()
                .courseName(course.getCourseName())
                .professorName(course.getProfessorName())
                .subjectName(course.getSubjectName())
                .pricePerMonth(course.getPricePerMonth())
                .pricePerSemester(course.getPricePerSemester())
//                .maxNumberOfBookings(course.getMaxNumberOfBookings())
                .build();
    }

    private StudentCoursePaperResponseModel buildStudentCoursePaperResponseModel(final CoursePaper coursePaper) {

        return StudentCoursePaperResponseModel
                .builder()
                .coursePaperName(coursePaper.getCoursePaperName())
                .professorName(coursePaper.getProfessorName())
                .subjectName(coursePaper.getSubjectName())
                .price(coursePaper.getPrice())
                .build();
    }


    private Student buildStudentEntity(final StudentCreationRequestModel studentCreationRequestModel) {

        return Student
                .builder()
                .studentName(studentCreationRequestModel.getStudentName())
                .mobileNumber(studentCreationRequestModel.getMobileNumber())
                .academicYear(studentCreationRequestModel.getAcademicYear())
                .facultyName(studentCreationRequestModel.getFacultyName())
                .status(Status.ACTIVE)
                .build();
    }
}

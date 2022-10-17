package com.example.libraryadminapp.core.domain.coursepaper.service.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.academicyear.repository.AcademicYearRepository;
import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.course.response.CourseListResponseModel;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperUpdateRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.response.CoursePaperListResponseModel;
import com.example.libraryadminapp.core.domain.coursepaper.service.CoursePaperService;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import com.example.libraryadminapp.core.domain.faculty.repository.FacultyRepository;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.core.firebase.service.impl.FirebaseMessagingServiceImpl;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CoursePaperServiceImpl implements CoursePaperService {
    private final CoursePaperRepository coursePaperRepository;
    private final AcademicYearRepository academicYearRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    private final FirebaseMessagingServiceImpl firebaseMessagingService;

    public void createCoursePaper(final CoursePaperCreationRequestModel coursePaperCreationRequestModel) {

        final AcademicYear academicYear = findByYear(coursePaperCreationRequestModel.getAcademicYear());
        final Faculty faculty = findByFacultyName(coursePaperCreationRequestModel.getFacultyName());

        final CoursePaper coursePaper = buildCoursePaperEntity(coursePaperCreationRequestModel, academicYear, faculty);

        coursePaperRepository.save(coursePaper);

        final List<Student> students = studentRepository.findAllByAcademicYearAndFacultyName(academicYear.getYear(), faculty.getName());

        students.forEach(student -> {
            try {

                firebaseMessagingService.sendNotification(student.getFcmToken(), "New Course paper Added", "Course Paper" + coursePaper.getCoursePaperName() + " has been added in our library!");
            } catch (FirebaseMessagingException e) {

//                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void updateCoursePaper(final CoursePaperUpdateRequestModel coursePaperUpdateRequestModel) {

        CoursePaper coursePaper = coursePaperRepository.findByName(coursePaperUpdateRequestModel.getCoursePaperName())
                .orElseThrow(() -> new IllegalArgumentException("Course Paper " + coursePaperUpdateRequestModel.getCoursePaperName() + " can't be found"));

        final AcademicYear academicYear = findByYear(coursePaperUpdateRequestModel.getAcademicYear());
        final Faculty faculty = findByFacultyName(coursePaperUpdateRequestModel.getFacultyName());

        coursePaper.setAcademicYear(academicYear);
        coursePaper.setFaculty(faculty);
        coursePaper.setSubjectName(coursePaperUpdateRequestModel.getSubjectName());
        coursePaper.setProfessorName(coursePaperUpdateRequestModel.getProfessorName());
        coursePaper.setPrice(coursePaperUpdateRequestModel.getPrice());
        coursePaperRepository.save(coursePaper);
    }

    @Override
    public void deleteCoursePaper(final String coursePaperName) {

        coursePaperRepository.deleteByCoursePaperName(coursePaperName);
    }

    @Override
    @Transactional
    public Page<CoursePaperListResponseModel> getAllCoursePapers() {

       Page<CoursePaper> coursePapers = coursePaperRepository.findAll();
       return coursePapers.map(coursePaper ->
               CoursePaperListResponseModel
                       .builder()
                       .coursePaperName(coursePaper.getCoursePaperName())
                       .subjectName(coursePaper.getSubjectName())
                       .professorName(coursePaper.getProfessorName())
                       .price(coursePaper.getPrice())
                       .academicYear(coursePaper.getAcademicYear().getYear())
                       .facultyName(coursePaper.getFaculty().getName())
                       .build()
       );
    }

    @Override
    public Page<CoursePaperListResponseModel> searchCoursePapers(final String searchName) {

        Page<CoursePaper> coursePapers = coursePaperRepository.findAllByCoursePaperNameOrSubjectNameProfessorName(searchName);

        return coursePapers.map(coursePaper ->
                CoursePaperListResponseModel
                        .builder()
                        .coursePaperName(coursePaper.getCoursePaperName())
                        .subjectName(coursePaper.getSubjectName())
                        .professorName(coursePaper.getProfessorName())
                        .price(coursePaper.getPrice())
                        .build()
        );
    }

    @Override
    public List<CoursePaperListResponseModel> searchCoursePapersByMobileNumber(String searchName, String mobileNumber) {

        final Student student = studentRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Student with mobile number " + mobileNumber + " can't be found"));

        final String academicYear = student.getAcademicYear();
        final String facultyName = student.getFacultyName();

        List<CoursePaper> coursePapers = coursePaperRepository.findAllByAcademicYearAndFacultyNameAndCoursePaperNameOrSubjectNameProfessorName(academicYear, facultyName, searchName);

        return coursePapers
                .stream()
                .map(coursePaper ->
                CoursePaperListResponseModel
                        .builder()
                        .coursePaperName(coursePaper.getCoursePaperName())
                        .subjectName(coursePaper.getSubjectName())
                        .professorName(coursePaper.getProfessorName())
                        .price(coursePaper.getPrice())
                        .build()
        )
                .collect(Collectors.toList());
    }

    @Override
    public List<CoursePaperListResponseModel> getAllAvailableCoursePapersForStudent(String mobileNumber) {

        final Student student = studentRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Student with mobile number " + mobileNumber + " can't be found"));

        final String academicYear = student.getAcademicYear();
        final String facultyName = student.getFacultyName();

        List<CoursePaper> coursePapers = coursePaperRepository.findAllByAcademicYearAndFacultyName(academicYear, facultyName);

        return coursePapers.stream()
                .map(coursePaper ->
                CoursePaperListResponseModel
                        .builder()
                        .coursePaperName(coursePaper.getCoursePaperName())
                        .subjectName(coursePaper.getSubjectName())
                        .professorName(coursePaper.getProfessorName())
                        .price(coursePaper.getPrice())
                        .academicYear(academicYear)
                        .facultyName(facultyName)
                        .build()
        )
                .collect(Collectors.toList());
    }

    private CoursePaper buildCoursePaperEntity(final CoursePaperCreationRequestModel coursePaperCreationRequestModel,
                                               final AcademicYear academicYear,
                                               final Faculty faculty) {

        return CoursePaper.builder()
                .coursePaperName(coursePaperCreationRequestModel.getCoursePaperName())
                .subjectName(coursePaperCreationRequestModel.getSubjectName())
                .professorName(coursePaperCreationRequestModel.getProfessorName())
                .price(coursePaperCreationRequestModel.getPrice())
                .academicYear(academicYear)
                .faculty(faculty)
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
}

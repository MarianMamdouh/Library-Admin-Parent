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

    public void createCoursePaper(final CoursePaperCreationRequestModel coursePaperCreationRequestModel) {

        final Optional<AcademicYear> academicYear = academicYearRepository.findByYear(coursePaperCreationRequestModel.getAcademicYear());
        final Optional<Faculty> faculty = facultyRepository.findByFacultyName(coursePaperCreationRequestModel.getFacultyName());

        final CoursePaper coursePaper = buildCoursePaperEntity(coursePaperCreationRequestModel, academicYear.get(), faculty.get() );

        coursePaperRepository.save(coursePaper);
    }

    @Override
    public void updateCoursePaper(final CoursePaperUpdateRequestModel coursePaperUpdateRequestModel) {

        Optional<CoursePaper> coursePaperOptional = coursePaperRepository.findByName(coursePaperUpdateRequestModel.getCoursePaperName());

        coursePaperOptional.ifPresent(coursePaper -> {

//            if (Objects.nonNull(coursePaper.getCourse())) {
//
//                System.out.println("error");
//                return;
//            }

            final Optional<AcademicYear> academicYear = academicYearRepository.findByYear(coursePaperUpdateRequestModel.getAcademicYear());
            final Optional<Faculty> faculty = facultyRepository.findByFacultyName(coursePaperUpdateRequestModel.getFacultyName());

            coursePaper.setAcademicYear(academicYear.get());
            coursePaper.setFaculty(faculty.get());
            coursePaper.setSubjectName(coursePaperUpdateRequestModel.getSubjectName());
            coursePaper.setProfessorName(coursePaperUpdateRequestModel.getProfessorName());
            coursePaper.setPrice(coursePaperUpdateRequestModel.getPrice());
            coursePaperRepository.save(coursePaper);
        });
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
                       .build());
    }

    @Override
    public List<CoursePaperListResponseModel> searchCoursePapers(String searchName) {

        List<CoursePaper> coursePapers = coursePaperRepository.findAllByCoursePaperNameOrSubjectNameProfessorName(searchName);

        return coursePapers.stream().map(coursePaper ->
                CoursePaperListResponseModel
                        .builder()
                        .coursePaperName(coursePaper.getCoursePaperName())
                        .subjectName(coursePaper.getSubjectName())
                        .professorName(coursePaper.getProfessorName())
                        .price(coursePaper.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CoursePaperListResponseModel> getAllAvailableCoursePapersForStudent(String studentName) {

        final Optional<Student> student = studentRepository.findByStudentName(studentName);

        final String academicYear = student.get().getAcademicYear();
        final String facultyName = student.get().getFacultyName();

        List<CoursePaper> coursePapers = coursePaperRepository.findAllByAcademicYearAndFacultyName(academicYear, facultyName);

        return coursePapers.stream().map(coursePaper ->
                CoursePaperListResponseModel
                        .builder()
                        .coursePaperName(coursePaper.getCoursePaperName())
                        .subjectName(coursePaper.getSubjectName())
                        .professorName(coursePaper.getProfessorName())
                        .price(coursePaper.getPrice())
                        .academicYear(academicYear)
                        .facultyName(facultyName)
                        .build())
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
}

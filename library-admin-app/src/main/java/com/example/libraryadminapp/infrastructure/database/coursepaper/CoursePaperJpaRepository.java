package com.example.libraryadminapp.infrastructure.database.coursepaper;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoursePaperJpaRepository extends JpaRepository<CoursePaper, Long> {

    Optional<CoursePaper> findByCoursePaperName(String name);

    List<CoursePaper> findAllByCoursePaperNameOrSubjectNameOrProfessorName(String coursePaperName, String subjectName, String professorName);
    List<CoursePaper> findAllByCourseId(long id);
    void deleteByCoursePaperName(String coursePaperName);

    List<CoursePaper> findAllByAcademicYear_YearAndFaculty_Name(String academicYear, String facultyName);
}

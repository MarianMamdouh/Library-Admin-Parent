package com.example.libraryadminapp.core.domain.coursepaper.repository;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CoursePaperRepository {

    CoursePaper save(CoursePaper coursePaper);

    List<CoursePaper> saveAll(List<CoursePaper> coursePapers);

    Optional<CoursePaper> findByName(String name);

    void deleteByCoursePaperName(String coursePaperName);

    List<CoursePaper> findAllByCourseId(long courseId);

    Page<CoursePaper> findAll();

    List<CoursePaper> findAllByCoursePaperNameOrSubjectNameProfessorName(String searchName);

    List<CoursePaper> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName);


}

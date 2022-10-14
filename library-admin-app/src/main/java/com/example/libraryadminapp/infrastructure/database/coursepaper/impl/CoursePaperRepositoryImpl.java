package com.example.libraryadminapp.infrastructure.database.coursepaper.impl;

import com.example.libraryadminapp.infrastructure.database.coursepaper.CoursePaperJpaRepository;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CoursePaperRepositoryImpl implements CoursePaperRepository {

    private final CoursePaperJpaRepository coursePaperJpaRepository;
    @Override
    public CoursePaper save(CoursePaper coursePaper) {

        return coursePaperJpaRepository.save(coursePaper);
    }

    @Override
    public List<CoursePaper> saveAll(List<CoursePaper> coursePapers) {

        return coursePaperJpaRepository.saveAll(coursePapers);
    }

    // todo throw an exception if not found
    @Override
    public Optional<CoursePaper> findByName(String name) {

        return coursePaperJpaRepository.findByCoursePaperName(name);
    }

    @Override
    @Transactional
    public Page<CoursePaper> findAll() {

        return coursePaperJpaRepository.findAll(Pageable.unpaged());
    }

    @Override
    public List<CoursePaper> findAllByCoursePaperNameOrSubjectNameProfessorName(final String searchName) {

        return coursePaperJpaRepository.findAllByCoursePaperNameOrSubjectNameOrProfessorName(searchName, searchName, searchName);
    }

    @Override
    public List<CoursePaper> findAllByAcademicYearAndFacultyName(String academicYear, String facultyName) {

        return coursePaperJpaRepository.findAllByAcademicYear_YearAndFaculty_Name(academicYear, facultyName);
    }

    @Override
    @Transactional
    public void deleteByCoursePaperName(final String coursePaperName) {

         coursePaperJpaRepository.deleteByCoursePaperName(coursePaperName);
    }

    @Override
    @Transactional
    public List<CoursePaper> findAllByCourseId(final long courseId) {

     return coursePaperJpaRepository.findAllByCourseId(courseId);
    }


}

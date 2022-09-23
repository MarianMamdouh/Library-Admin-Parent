package com.example.libraryadminapp.infrastructure.database.professor;

import com.example.libraryadminapp.core.domain.coursepaper.CoursePaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoursePaperJpaRepository extends JpaRepository<CoursePaper, Long> {

    Optional<CoursePaper> findByCoursePaperName(String name);

    List<CoursePaper> findAllByCourseId(long id);
    void deleteByCoursePaperName(String coursePaperName);



}

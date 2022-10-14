package com.example.libraryadminapp.infrastructure.database.academicyear;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicYearJpaRepository extends JpaRepository<AcademicYear, Long> {

    Optional<AcademicYear> findByYear(String year);
}

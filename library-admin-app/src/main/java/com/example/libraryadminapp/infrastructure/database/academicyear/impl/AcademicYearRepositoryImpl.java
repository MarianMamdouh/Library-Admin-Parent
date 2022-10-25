package com.example.libraryadminapp.infrastructure.database.academicyear.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.academicyear.repository.AcademicYearRepository;
import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.infrastructure.database.academicyear.AcademicYearJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AcademicYearRepositoryImpl implements AcademicYearRepository {

    private final AcademicYearJpaRepository academicYearJpaRepository;

    @Override
    public Optional<AcademicYear> findByYear(String year) {

        return academicYearJpaRepository.findByYear(year);
    }

    @Override
    public void createAcademicYear(AcademicYear year) {

        academicYearJpaRepository.save(year);
    }

    @Override
    @Transactional
    public void deleteAcademicYear(String year) {

        academicYearJpaRepository.deleteByYear(year);
    }

    @Override
    public List<AcademicYear> getAllYears() {
        return  academicYearJpaRepository.findAll();
    }
}

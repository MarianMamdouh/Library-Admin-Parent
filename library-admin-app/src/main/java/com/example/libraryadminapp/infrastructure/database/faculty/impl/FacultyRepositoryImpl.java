package com.example.libraryadminapp.infrastructure.database.faculty.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import com.example.libraryadminapp.core.domain.faculty.repository.FacultyRepository;
import com.example.libraryadminapp.infrastructure.database.academicyear.AcademicYearJpaRepository;
import com.example.libraryadminapp.infrastructure.database.faculty.FacultyJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FacultyRepositoryImpl implements FacultyRepository {

    private final FacultyJpaRepository facultyJpaRepository;

    @Override
    public Optional<Faculty> findByFacultyName(String name) {

        return facultyJpaRepository.findByName(name);
    }

    @Override
    public void createFaculty(Faculty faculty) {

        facultyJpaRepository.save(faculty);

    }

    @Override
    public List<Faculty> getAllFaculties() {

        return facultyJpaRepository.findAll();
    }
}

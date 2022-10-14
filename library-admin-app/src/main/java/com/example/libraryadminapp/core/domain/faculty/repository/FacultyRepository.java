package com.example.libraryadminapp.core.domain.faculty.repository;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository {

    Optional<Faculty> findByFacultyName(String name);

    void createFaculty(Faculty year);

    List<Faculty> getAllFaculties();
}

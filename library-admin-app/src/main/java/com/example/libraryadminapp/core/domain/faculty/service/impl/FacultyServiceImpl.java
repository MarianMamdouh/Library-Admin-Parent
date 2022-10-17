package com.example.libraryadminapp.core.domain.faculty.service.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.academicyear.repository.AcademicYearRepository;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import com.example.libraryadminapp.core.domain.faculty.repository.FacultyRepository;
import com.example.libraryadminapp.core.domain.faculty.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public void createFaculty(final String facultyName) {

        if (facultyRepository.getAllFaculties()
                .stream()
                .anyMatch(faculty-> faculty.getName().equals(facultyName))) {

            throw new IllegalArgumentException("Faculty " + facultyName + " already exists!");
        }

        final Faculty faculty = Faculty
                .builder()
                .name(facultyName)
                .build();

        facultyRepository.createFaculty(faculty);
    }

    @Override
    public Page<String> getAllFaculties() {

        return facultyRepository.getAllFaculties().map(Faculty::getName);
    }
}


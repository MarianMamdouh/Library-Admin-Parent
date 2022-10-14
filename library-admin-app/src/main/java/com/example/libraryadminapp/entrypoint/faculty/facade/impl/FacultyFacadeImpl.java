package com.example.libraryadminapp.entrypoint.faculty.facade.impl;

import com.example.libraryadminapp.core.domain.academicyear.service.AcademicYearService;
import com.example.libraryadminapp.core.domain.faculty.service.FacultyService;
import com.example.libraryadminapp.entrypoint.faculty.facade.FacultyFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FacultyFacadeImpl implements FacultyFacade {

    private final FacultyService facultyService;


    @Override
    public void createFaculty(String facultyName) {

        facultyService.createFaculty(facultyName);

    }

    @Override
    public List<String> getAllFaculties() {

       return facultyService.getAllFaculties();
    }
}

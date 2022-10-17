package com.example.libraryadminapp.entrypoint.faculty.facade;

import org.springframework.data.domain.Page;

import java.util.List;

public interface FacultyFacade {

    void createFaculty(String facultyName);

    Page<String> getAllFaculties();
}

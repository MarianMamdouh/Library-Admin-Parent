package com.example.libraryadminapp.core.domain.faculty.service;

import org.springframework.data.domain.Page;

import java.util.List;

public interface FacultyService {

    void createFaculty(String facultyName);

    Page<String> getAllFaculties();
}

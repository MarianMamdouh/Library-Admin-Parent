package com.example.libraryadminapp.core.domain.faculty.service;

import java.util.List;

public interface FacultyService {

    void createFaculty(String facultyName);

    List<String> getAllFaculties();
}

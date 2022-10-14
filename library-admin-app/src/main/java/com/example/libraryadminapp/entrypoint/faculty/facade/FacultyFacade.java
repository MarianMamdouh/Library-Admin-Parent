package com.example.libraryadminapp.entrypoint.faculty.facade;

import java.util.List;

public interface FacultyFacade {

    void createFaculty(String facultyName);

    List<String> getAllFaculties();
}

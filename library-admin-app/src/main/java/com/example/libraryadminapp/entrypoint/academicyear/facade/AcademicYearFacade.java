package com.example.libraryadminapp.entrypoint.academicyear.facade;

import java.util.List;

public interface AcademicYearFacade {

    void createAcademicYear(String year);

    List<String> getAllYears();
}

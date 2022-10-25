package com.example.libraryadminapp.core.domain.academicyear.service;

import java.util.List;

public interface AcademicYearService {

    void createAcademicYear(String year);

    void deleteAcademicYear(String year);

    List<String> getAllYears();
}

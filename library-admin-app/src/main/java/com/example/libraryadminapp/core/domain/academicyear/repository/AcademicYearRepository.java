package com.example.libraryadminapp.core.domain.academicyear.repository;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;
import java.util.Optional;

public interface AcademicYearRepository {

    Optional<AcademicYear> findByYear(String year);

    void createAcademicYear(AcademicYear year);

    List<AcademicYear> getAllYears();

}

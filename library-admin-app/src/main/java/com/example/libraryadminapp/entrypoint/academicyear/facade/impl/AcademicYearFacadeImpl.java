package com.example.libraryadminapp.entrypoint.academicyear.facade.impl;

import com.example.libraryadminapp.core.domain.academicyear.service.AcademicYearService;
import com.example.libraryadminapp.entrypoint.academicyear.facade.AcademicYearFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AcademicYearFacadeImpl implements AcademicYearFacade {

    private final AcademicYearService academicYearService;


    @Override
    public void createAcademicYear(String year) {

        academicYearService.createAcademicYear(year);

    }

    @Override
    public List<String> getAllYears() {

       return academicYearService.getAllYears();
    }
}

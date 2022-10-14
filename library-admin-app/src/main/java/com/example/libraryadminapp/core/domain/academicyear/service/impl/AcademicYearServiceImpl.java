package com.example.libraryadminapp.core.domain.academicyear.service.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.academicyear.repository.AcademicYearRepository;
import com.example.libraryadminapp.core.domain.academicyear.service.AcademicYearService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public void createAcademicYear(String year) {

        final AcademicYear academicYear = AcademicYear
                .builder()
                .year(year)
                .build();

        academicYearRepository.createAcademicYear(academicYear);
    }

    @Override
    public List<String> getAllYears() {

        return academicYearRepository.getAllYears()
                .stream()
                .map(AcademicYear::getYear)
                .collect(Collectors.toList());
    }


}


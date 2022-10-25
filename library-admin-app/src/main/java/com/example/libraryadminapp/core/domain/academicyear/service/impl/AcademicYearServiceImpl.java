package com.example.libraryadminapp.core.domain.academicyear.service.impl;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.academicyear.repository.AcademicYearRepository;
import com.example.libraryadminapp.core.domain.academicyear.service.AcademicYearService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AcademicYearServiceImpl implements AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    @Override
    public void createAcademicYear(final String year) {

       if (academicYearRepository.getAllYears()
               .stream()
               .anyMatch(academicYear1 -> academicYear1.getYear().equals(year))) {

           throw new IllegalArgumentException("Year " + year + " already exists!");
       }

        final AcademicYear academicYear = AcademicYear
                .builder()
                .year(year)
                .build();

        academicYearRepository.createAcademicYear(academicYear);
    }

    @Override
    @Transactional
    public void deleteAcademicYear(String year) {

        academicYearRepository.deleteAcademicYear(year);
    }

    @Override
    public List<String> getAllYears() {

        return academicYearRepository.getAllYears()
                .stream()
                .map(AcademicYear::getYear)
                .collect(Collectors.toList());
    }
}


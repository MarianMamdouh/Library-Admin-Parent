package com.example.libraryadminapp.core.domain.coursepaper.service.impl;

import com.example.libraryadminapp.core.domain.coursepaper.CoursePaper;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.response.CoursePaperListResponseModel;
import com.example.libraryadminapp.core.domain.coursepaper.service.CoursePaperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoursePaperServiceImpl implements CoursePaperService {
    private final CoursePaperRepository coursePaperRepository;

    public void createCoursePaper(final CoursePaperCreationRequestModel coursePaperCreationRequestModel) {

        final CoursePaper coursePaper = buildCoursePaperEntity(coursePaperCreationRequestModel);

        coursePaperRepository.save(coursePaper);

    }

    @Override
    public Page<CoursePaperListResponseModel> getAllCoursePapers() {

       Page<CoursePaper> coursePapers = coursePaperRepository.findAll();
       return coursePapers.map(coursePaper ->
               CoursePaperListResponseModel
                       .builder()
                       .coursePaperName(coursePaper.getCoursePaperName())
                       .subjectName(coursePaper.getSubjectName())
                       .professorName(coursePaper.getProfessorName())
                       .price(coursePaper.getPrice())
                       .numOfCopies(coursePaper.getNumOfCopies())
                       .build());
    }

    private CoursePaper buildCoursePaperEntity(final CoursePaperCreationRequestModel coursePaperCreationRequestModel) {

        return CoursePaper.builder()
                .coursePaperName(coursePaperCreationRequestModel.getCoursePaperName())
                .subjectName(coursePaperCreationRequestModel.getSubjectName())
                .professorName(coursePaperCreationRequestModel.getProfessorName())
                .price(coursePaperCreationRequestModel.getPrice())
                .build();
    }
}

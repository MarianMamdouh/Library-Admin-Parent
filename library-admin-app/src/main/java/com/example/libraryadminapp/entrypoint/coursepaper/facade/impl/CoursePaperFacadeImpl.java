package com.example.libraryadminapp.entrypoint.coursepaper.facade.impl;

import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.facade.CoursePaperFacade;
import com.example.libraryadminapp.core.domain.coursepaper.request.CoursePaperCreationRequestModel;
import com.example.libraryadminapp.core.domain.coursepaper.service.CoursePaperService;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.response.CoursePaperListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CoursePaperFacadeImpl implements CoursePaperFacade {

    private final CoursePaperService coursePaperService;

    @Override
    public void createCoursePaper(final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO) {

        final CoursePaperCreationRequestModel coursePaperCreationRequestModel = buildCoursePaperCreationRequestModel(coursePaperCreationRequestDTO);
        coursePaperService.createCoursePaper(coursePaperCreationRequestModel);
    }

    @Override
    public Page<CoursePaperListResponseDTO> getAllCoursePapers() {

        return coursePaperService.getAllCoursePapers().map(coursePaperListResponseModel ->
                CoursePaperListResponseDTO
                        .builder()
                        .courseName(coursePaperListResponseModel.getCoursePaperName())
                        .subjectName(coursePaperListResponseModel.getSubjectName())
                        .professorName(coursePaperListResponseModel.getProfessorName())
                        .price(coursePaperListResponseModel.getPrice())
                        .numOfCopies(coursePaperListResponseModel.getNumOfCopies())
                        .build());
    }

    private CoursePaperCreationRequestModel buildCoursePaperCreationRequestModel(final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO) {

        return CoursePaperCreationRequestModel
                .builder()
                .coursePaperName(coursePaperCreationRequestDTO.getCoursePaperName())
                .subjectName(coursePaperCreationRequestDTO.getSubjectName())
                .professorName(coursePaperCreationRequestDTO.getProfessorName())
                .price(coursePaperCreationRequestDTO.getPrice())
                .build();
    }
}

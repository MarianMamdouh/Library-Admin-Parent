package com.example.libraryadminparent.entrypoint.professor.facade.impl;

import com.example.libraryadminparent.core.domain.professor.request.ProfessorCreationRequestModel;
import com.example.libraryadminparent.core.domain.professor.service.ProfessorService;
import com.example.libraryadminparent.entrypoint.professor.controller.request.ProfessorCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.professor.controller.response.ProfessorListResponseDTO;
import com.example.libraryadminparent.entrypoint.professor.facade.ProfessorFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfessorFacadeImpl implements ProfessorFacade {

    private final ProfessorService professorService;

    @Override
    public void createProfessor(final ProfessorCreationRequestDTO professorCreationRequestDTO) {

        final ProfessorCreationRequestModel professorCreationRequestModel = buildProfessorCreationRequestModel(professorCreationRequestDTO);
        professorService.createProfessor(professorCreationRequestModel);
    }

    @Override
    public Page<ProfessorListResponseDTO> getAllProfessors() {

        return professorService.getAllProfessors().map(professorListResponseModel ->
                ProfessorListResponseDTO
                        .builder()
                        .professorName(professorListResponseModel.getProfessorName())
                        .build());
    }

    private ProfessorCreationRequestModel buildProfessorCreationRequestModel(final ProfessorCreationRequestDTO professorCreationRequestDTO) {

        return ProfessorCreationRequestModel
                .builder()
                .professorName(professorCreationRequestDTO.getProfessorName())
                .build();
    }
}

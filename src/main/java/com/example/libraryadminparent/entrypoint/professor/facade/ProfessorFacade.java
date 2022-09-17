package com.example.libraryadminparent.entrypoint.professor.facade;

import com.example.libraryadminparent.entrypoint.professor.controller.request.ProfessorCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.professor.controller.response.ProfessorListResponseDTO;
import org.springframework.data.domain.Page;

public interface ProfessorFacade {

    void createProfessor(final ProfessorCreationRequestDTO professorCreationRequestDTO);

    Page<ProfessorListResponseDTO> getAllProfessors();
}

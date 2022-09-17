package com.example.libraryadminparent.core.domain.professor.service;

import com.example.libraryadminparent.core.domain.professor.request.ProfessorCreationRequestModel;
import com.example.libraryadminparent.core.domain.professor.response.ProfessorListResponseModel;
import org.springframework.data.domain.Page;

public interface ProfessorService {

    void createProfessor(final ProfessorCreationRequestModel professorCreationRequestModel);

    Page<ProfessorListResponseModel> getAllProfessors();
}

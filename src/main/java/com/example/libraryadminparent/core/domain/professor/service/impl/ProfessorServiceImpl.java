package com.example.libraryadminparent.core.domain.professor.service.impl;

import com.example.libraryadminparent.core.domain.course.entity.Course;
import com.example.libraryadminparent.core.domain.course.repository.CourseRepository;
import com.example.libraryadminparent.core.domain.course.request.CourseCreationRequestModel;
import com.example.libraryadminparent.core.domain.professor.Professor;
import com.example.libraryadminparent.core.domain.professor.repository.ProfessorRepository;
import com.example.libraryadminparent.core.domain.professor.request.ProfessorCreationRequestModel;
import com.example.libraryadminparent.core.domain.professor.response.ProfessorListResponseModel;
import com.example.libraryadminparent.core.domain.professor.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final ProfessorRepository professorRepository;

    public void createProfessor(final ProfessorCreationRequestModel professorCreationRequestModel) {

        final Professor professor = buildProfessorEntity(professorCreationRequestModel);

        professorRepository.save(professor);

    }

    @Override
    public Page<ProfessorListResponseModel> getAllProfessors() {

       Page<Professor> professors = professorRepository.findAll();
       return professors.map(professor ->
               ProfessorListResponseModel
                       .builder()
                       .professorName(professor.getName())
                       .build());
    }

    private Professor buildProfessorEntity(final ProfessorCreationRequestModel professorCreationRequestModel) {

        return Professor.builder()
                .name(professorCreationRequestModel.getProfessorName())
                .build();
    }
}

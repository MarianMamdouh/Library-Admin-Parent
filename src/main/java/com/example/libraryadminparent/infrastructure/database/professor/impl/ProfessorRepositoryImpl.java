package com.example.libraryadminparent.infrastructure.database.professor.impl;

import com.example.libraryadminparent.core.domain.professor.Professor;
import com.example.libraryadminparent.core.domain.professor.repository.ProfessorRepository;
import com.example.libraryadminparent.infrastructure.database.professor.ProfessorJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProfessorRepositoryImpl implements ProfessorRepository {

    private final ProfessorJpaRepository professorJpaRepository;
    @Override
    public Professor save(Professor professor) {

        return professorJpaRepository.save(professor);
    }

    // todo throw an exception if not found
    @Override
    public Professor findByName(String name) {
        return professorJpaRepository.findByName(name).get();
    }

    @Override
    public Page<Professor> findAll() {

        return professorJpaRepository.findAll(Pageable.unpaged());
    }
}

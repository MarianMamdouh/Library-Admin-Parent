package com.example.libraryadminparent.core.domain.professor.repository;

import com.example.libraryadminparent.core.domain.professor.Professor;
import org.springframework.data.domain.Page;

public interface ProfessorRepository {

    Professor save(Professor professor);

    Professor findByName(String name);

    Page<Professor> findAll();
}

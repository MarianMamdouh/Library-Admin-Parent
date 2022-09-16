package com.example.libraryadminparent.core.domain.professor.repository;

import com.example.libraryadminparent.core.domain.professor.Professor;

public interface ProfessorRepository {

    Professor save(Professor professor);

    Professor findByName(String name);
}

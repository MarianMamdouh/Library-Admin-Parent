package com.example.libraryadminparent.infrastructure.database.professor;

import com.example.libraryadminparent.core.domain.professor.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorJpaRepository extends JpaRepository<Professor, Long> {

    Optional<Professor> findByName(String name);


}

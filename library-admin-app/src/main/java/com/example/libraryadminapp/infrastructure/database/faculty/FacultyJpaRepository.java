package com.example.libraryadminapp.infrastructure.database.faculty;

import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyJpaRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByName(String anme);
}

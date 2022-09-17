package com.example.libraryadminparent.entrypoint.professor.controller;

import com.example.libraryadminparent.entrypoint.course.controller.CourseController;
import com.example.libraryadminparent.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.course.facade.CourseFacade;
import com.example.libraryadminparent.entrypoint.professor.controller.request.ProfessorCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.professor.controller.response.ProfessorListResponseDTO;
import com.example.libraryadminparent.entrypoint.professor.facade.ProfessorFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = ProfessorController.ROOT_PATH)
@AllArgsConstructor
public class ProfessorController {

    static final String ROOT_PATH = "/professors";

    private final ProfessorFacade professorFacade;

    @PostMapping
    public ResponseEntity<?> createProfessor(
            @Valid @RequestBody final ProfessorCreationRequestDTO professorCreationRequestDTO) throws Exception {

        professorFacade.createProfessor(professorCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProfessorListResponseDTO> getAllProfessors() throws Exception {

       return professorFacade.getAllProfessors();
    }


}

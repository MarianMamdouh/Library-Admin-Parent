package com.example.libraryadminparent.entrypoint.professor.controller.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class ProfessorCreationRequestDTO {

    @NotBlank
    private String professorName;
}

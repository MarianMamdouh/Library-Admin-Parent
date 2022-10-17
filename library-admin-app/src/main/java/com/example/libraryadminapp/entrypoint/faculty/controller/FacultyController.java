package com.example.libraryadminapp.entrypoint.faculty.controller;

import com.example.libraryadminapp.entrypoint.academicyear.facade.AcademicYearFacade;
import com.example.libraryadminapp.entrypoint.faculty.facade.FacultyFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = FacultyController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin
public class FacultyController {

    static final String ROOT_PATH = "/faculty";

    private final FacultyFacade facultyFacade;

    @PostMapping
    public ResponseEntity<?> createFaculty(
            @RequestParam final String facultyName) throws Exception {

        facultyFacade.createFaculty(facultyName);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<String> getAllFaculties() throws Exception {

        return facultyFacade.getAllFaculties();
    }
}

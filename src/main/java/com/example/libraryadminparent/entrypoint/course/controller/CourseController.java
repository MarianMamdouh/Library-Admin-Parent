package com.example.libraryadminparent.entrypoint.course.controller;

import com.example.libraryadminparent.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.course.facade.CourseFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = CourseController.ROOT_PATH)
@AllArgsConstructor
public class CourseController {

    static final String ROOT_PATH = "/courses";

    private final CourseFacade courseFacade;

    @PostMapping
    public ResponseEntity<?> createCourse(
            @Valid @RequestBody final CourseCreationRequestDTO courseCreationRequestDTO) throws Exception {

        courseFacade.createCourse(courseCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

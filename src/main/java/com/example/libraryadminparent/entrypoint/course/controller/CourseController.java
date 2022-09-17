package com.example.libraryadminparent.entrypoint.course.controller;

import com.example.libraryadminparent.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminparent.entrypoint.course.controller.response.CourseListResponseDTO;
import com.example.libraryadminparent.entrypoint.course.facade.CourseFacade;
import com.example.libraryadminparent.entrypoint.professor.controller.response.ProfessorListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseListResponseDTO> getAllCourses() throws Exception {

        return courseFacade.getAllCourses();
    }
}

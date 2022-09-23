package com.example.libraryadminapp.entrypoint.course.controller;

import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.facade.CourseFacade;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = CourseController.ROOT_PATH)
@AllArgsConstructor
public class CourseController {

    static final String ROOT_PATH = "/courses";

    private final CourseFacade courseFacade;

    @PostMapping
    public ResponseEntity<?> createCourse(
            @RequestBody final CourseCreationRequestDTO courseCreationRequestDTO) throws Exception {

        courseFacade.createCourse(courseCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCourse(
            @RequestBody final CourseUpdateRequestDTO courseUpdateRequestDTO) throws Exception {

        courseFacade.updateCourse(courseUpdateRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCourse(
            @RequestParam final String courseName) throws Exception {

        courseFacade.deleteCourse(courseName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourseListResponseDTO> getAllCourses() throws Exception {

        return courseFacade.getAllCourses();
    }
}

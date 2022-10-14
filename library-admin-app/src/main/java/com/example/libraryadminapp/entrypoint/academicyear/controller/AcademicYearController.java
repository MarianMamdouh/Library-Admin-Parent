package com.example.libraryadminapp.entrypoint.academicyear.controller;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.entrypoint.academicyear.facade.AcademicYearFacade;
import com.example.libraryadminapp.entrypoint.course.controller.CourseController;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import com.example.libraryadminapp.entrypoint.course.facade.CourseFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = AcademicYearController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin
public class AcademicYearController {

    static final String ROOT_PATH = "/academicYear";

    private final AcademicYearFacade academicYearFacade;

    @PostMapping
    public ResponseEntity<?> createAcademicYear(
            @RequestParam final String year) throws Exception {

        academicYearFacade.createAcademicYear(year);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllYears() throws Exception {

        return academicYearFacade.getAllYears();
    }
}

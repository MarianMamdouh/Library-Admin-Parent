package com.example.libraryadminapp.entrypoint.coursepaper.controller;

import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.facade.CoursePaperFacade;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.response.CoursePaperListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = CoursePaperController.ROOT_PATH)
@AllArgsConstructor
public class CoursePaperController {

    static final String ROOT_PATH = "/coursePapers";

    private final CoursePaperFacade coursePaperFacade;

    @PostMapping
    public ResponseEntity<?> createCoursePaper(
            @Valid @RequestBody final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO) throws Exception {

        coursePaperFacade.createCoursePaper(coursePaperCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaperListResponseDTO> getAllCoursePapers() throws Exception {

       return coursePaperFacade.getAllCoursePapers();
    }


}

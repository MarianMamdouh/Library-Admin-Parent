package com.example.libraryadminapp.entrypoint.coursepaper.controller;

import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.request.CoursePaperUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.coursepaper.facade.CoursePaperFacade;
import com.example.libraryadminapp.entrypoint.coursepaper.controller.response.CoursePaperListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = CoursePaperController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin
public class CoursePaperController {

    static final String ROOT_PATH = "/coursePapers";

    private final CoursePaperFacade coursePaperFacade;

    @PostMapping
    public ResponseEntity<?> createCoursePaper(
            @Valid @RequestBody final CoursePaperCreationRequestDTO coursePaperCreationRequestDTO) throws Exception {

        coursePaperFacade.createCoursePaper(coursePaperCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCoursePaper(
            @Valid @RequestBody final CoursePaperUpdateRequestDTO coursePaperUpdateRequestDTO) throws Exception {

        coursePaperFacade.updateCoursePaper(coursePaperUpdateRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCoursePaper(@RequestParam final String coursePaperName) throws Exception {

        coursePaperFacade.deleteCoursePaper(coursePaperName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CoursePaperListResponseDTO> getAllCoursePapers() throws Exception {

       return coursePaperFacade.getAllCoursePapers();
    }

    @GetMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    public List<CoursePaperListResponseDTO> getAllAvailableCoursePapersForStudent(@RequestParam final String studentName) throws Exception {

        return coursePaperFacade.getAllAvailableCoursePapersForStudent(studentName);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CoursePaperListResponseDTO> searchCoursePapers(@RequestParam("searchName") String searchName) throws Exception {

        return coursePaperFacade.searchCoursePapers(searchName);
    }


}

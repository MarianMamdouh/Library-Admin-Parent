package com.example.libraryadminapp.entrypoint.student.controller;

import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;
import com.example.libraryadminapp.entrypoint.student.facade.StudentFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = StudentController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin
public class StudentController {

    static final String ROOT_PATH = "/students";

    private final StudentFacade studentFacade;

    @PostMapping
    public ResponseEntity<?> createStudent(
            @Valid @RequestBody final StudentCreationRequestDTO studentCreationRequestDTO) throws Exception {

        studentFacade.createStudent(studentCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/assignCourseToStudent")
    public ResponseEntity<?> assignCourseToStudent(
            @RequestParam("courseName") final String courseName, @RequestParam("studentName") final String studentName, @RequestParam("courseSlotId") final Long courseSlotId) throws Exception {

        studentFacade.assignCourseToStudent(courseName, studentName, courseSlotId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/assignCoursePaperToStudent")
    public ResponseEntity<?> assignCoursePaperToStudent(
            @RequestParam("coursePaperName") final String coursePaperName, @RequestParam("studentName") final String studentName) throws Exception {

        studentFacade.assignCoursePaperToStudent(coursePaperName, studentName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCourseResponseDTO> getAllCoursesBookings(@RequestParam("studentName") final String studentName) throws Exception {

        return studentFacade.getAllCoursesBookings(studentName);
    }

    @GetMapping(value = "/coursePapers")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(@RequestParam("studentName") final String studentName) throws Exception {

        return studentFacade.getAllCoursePapersBookings(studentName);
    }
}

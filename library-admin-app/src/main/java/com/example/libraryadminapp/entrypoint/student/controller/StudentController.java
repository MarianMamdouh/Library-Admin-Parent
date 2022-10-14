package com.example.libraryadminapp.entrypoint.student.controller;

import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.CoursePaymentInfoResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;
import com.example.libraryadminapp.entrypoint.student.facade.StudentFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PostMapping("/verifyStudentMobileNumber")
    public ResponseEntity<?> verifyStudentMobileNumber(
            @RequestParam("studentMobileNumber") final String studentMobileNumber) throws Exception {

        studentFacade.verifyStudentMobileNumber(studentMobileNumber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/assignCourseToStudent")
    public ResponseEntity<Integer> assignCourseToStudent(
            @RequestParam("courseName") final String courseName, @RequestParam("studentName") final String studentName, @RequestParam("courseSlotId") final Long courseSlotId) throws Exception {

        return new ResponseEntity<>(
                studentFacade.assignCourseToStudent(courseName, studentName, courseSlotId), HttpStatus.OK);
    }

    @PutMapping(value = "/assignCoursePaperToStudent")
    public ResponseEntity<Integer> assignCoursePaperToStudent(
            @RequestParam("coursePaperName") final String coursePaperName, @RequestParam("studentName") final String studentName) throws Exception {


        return new ResponseEntity<>(studentFacade.assignCoursePaperToStudent(coursePaperName, studentName), HttpStatus.OK);
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

    @GetMapping(value = "/courses/paymentInfos")
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaymentInfoResponseDTO> getAllCoursesPaymentInfo() throws Exception {

        return studentFacade.getAllCoursesPaymentInfo();
    }

    @GetMapping(value = "/coursePapers/paymentInfos")
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaymentInfoResponseDTO> getAllCoursePapersPaymentInfo() throws Exception {

        return studentFacade.getAllCoursePapersPaymentInfo();
    }

    @GetMapping(value = "/paymentInfos/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaymentInfoResponseDTO> searchByPaymentInfoNumber(@RequestParam("paymentInfoNumber") final Integer paymentInfoNumber) throws Exception {

        return studentFacade.searchByPaymentInfoNumber(paymentInfoNumber);
    }
}

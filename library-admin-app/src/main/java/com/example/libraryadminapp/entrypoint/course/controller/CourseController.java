package com.example.libraryadminapp.entrypoint.course.controller;

import com.example.libraryadminapp.entrypoint.course.controller.request.CoursePaperRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseSlotRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseUpdateRequestDTO;
import com.example.libraryadminapp.entrypoint.course.facade.CourseFacade;
import com.example.libraryadminapp.entrypoint.course.controller.request.CourseCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.course.controller.response.CourseListResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = CourseController.ROOT_PATH)
@AllArgsConstructor
@CrossOrigin
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

    @PutMapping("/{courseName}/coursePaper")
    public ResponseEntity<?> addCoursePaperToCourse(
            @PathVariable final String courseName,
            @RequestBody final CoursePaperRequestDTO coursePaperRequestDTO) throws Exception {

        courseFacade.addCoursePaperToCourse(courseName, coursePaperRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{courseName}/coursePaper")
    public ResponseEntity<?> deleteCoursePaperFromCourse(
            @PathVariable final String courseName,
            @RequestParam final String coursePaperName) throws Exception {

        courseFacade.deleteCoursePaperFromCourse(courseName, coursePaperName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{courseName}/courseSlot")
    public ResponseEntity<?> deleteCoursePaperFromCourse(
            @PathVariable final String courseName,
            @RequestParam final Long courseSlotId) throws Exception {

        courseFacade.deleteCourseSlotFromCourse(courseName, courseSlotId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{courseName}/courseSlot")
    public ResponseEntity<?> addCourseSlotToCourse(
            @PathVariable final String courseName,
            @RequestBody final CourseSlotRequestDTO courseSlotRequestDTO) throws Exception {

        courseFacade.addCourseSlotToCourse(courseName, courseSlotRequestDTO);

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

    @GetMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseListResponseDTO> getAllAvailableCoursesForStudent(@RequestParam final String studentName) throws Exception {

        return courseFacade.getAllAvailableCoursesForStudent(studentName);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseListResponseDTO> searchCourses(@RequestParam("searchName") String searchName) throws Exception {

        return courseFacade.searchCourses(searchName);
    }
}

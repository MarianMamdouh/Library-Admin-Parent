package com.example.libraryadminapp.entrypoint.student.controller;

import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentLoginRequestDTO;
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

    private final StudentRepository studentRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody final StudentCreationRequestDTO studentCreationRequestDTO) throws Exception {

        studentFacade.createStudent(studentCreationRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resendOTP")
    public ResponseEntity<?> resendOTP(@RequestParam String mobileNumber) throws Exception {

        studentFacade.resendOTP(mobileNumber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody final StudentLoginRequestDTO studentLoginDTO) throws Exception {


        return new ResponseEntity<>(studentFacade.login(studentLoginDTO), HttpStatus.OK);
    }


    @PostMapping("/verifyOTP")
    public ResponseEntity<?> verifyStudentOTP(
            @RequestParam("oneTimePassword") final String oneTimePassword, @RequestParam("mobileNumber") final String mobileNumber) throws Exception {

        studentFacade.verifyStudentOTP(oneTimePassword, mobileNumber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/setupFCMToken")
    public ResponseEntity<?> setFCMToken(
            @RequestParam("fcmToken") final String fcmToken, @RequestParam("mobileNumber") final String mobileNumber) throws Exception {

        studentFacade.setFCMToken(fcmToken, mobileNumber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/assignCourseToStudent")
    public ResponseEntity<Integer> assignCourseToStudent(
            @RequestParam("courseName") final String courseName, @RequestParam("mobileNumber") final String mobileNumber, @RequestParam("courseSlotId") final Long courseSlotId) throws Exception {

        return new ResponseEntity<>(
                studentFacade.assignCourseToStudent(courseName, mobileNumber, courseSlotId), HttpStatus.OK);
    }

    @PutMapping(value = "/assignCoursePaperToStudent")
    public ResponseEntity<Integer> assignCoursePaperToStudent(
            @RequestParam("coursePaperName") final String coursePaperName, @RequestParam("mobileNumber") final String mobileNumber,
            @RequestParam("deliveryAddress") final String deliveryAddress) throws Exception {

        return new ResponseEntity<>(
                studentFacade.assignCoursePaperToStudent(coursePaperName, mobileNumber, deliveryAddress), HttpStatus.OK);
    }

    @GetMapping(value = "/courses")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCourseResponseDTO> getAllCoursesBookings(@RequestParam("mobileNumber") final String mobileNumber) throws Exception {

        return studentFacade.getAllCoursesBookings(mobileNumber);
    }

    @GetMapping(value = "/coursePapers")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(@RequestParam("mobileNumber") final String mobileNumber) throws Exception {

        return studentFacade.getAllCoursePapersBookings(mobileNumber);
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

    @GetMapping(value = "/assignedCourses")
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaymentInfoResponseDTO> getAllStudentsAssignedCoursesForUI() throws Exception {

        return studentFacade.findAllByCourseSlotsExists();
    }

    @GetMapping(value = "/assignedCoursePapers")
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaymentInfoResponseDTO> getAllStudentsAssignedCoursePapersForUI() throws Exception {

        return studentFacade.findAllByCoursePapersExists();
    }

    @GetMapping(value = "/paymentInfos/search")
    @ResponseStatus(HttpStatus.OK)
    public CoursePaymentInfoResponseDTO searchByPaymentInfoNumber(@RequestParam("paymentInfoNumber") final Integer paymentInfoNumber) throws Exception {

        return studentFacade.searchByPaymentInfoNumber(paymentInfoNumber);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteAll() throws Exception {

         studentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/coursePapers/paymentInfos")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCoursePaperPaymentInfo(@RequestParam("paymentInfoNumber") final Integer paymentInfoNumber) throws Exception {

        studentFacade.deleteCoursePaperPaymentInfo(paymentInfoNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/courses/paymentInfos")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCoursePaymentInfo(@RequestParam("paymentInfoNumber") final Integer paymentInfoNumber) throws Exception {

        studentFacade.deleteCoursePaymentInfo(paymentInfoNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/courses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> unassignStudentFromCourse(@RequestParam("courseName") final String courseName, @RequestParam("mobileNumber") String mobileNumber) throws Exception {

        studentFacade.unassignStudentFromCourse(courseName, mobileNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/coursePapers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> unassignStudentFromCoursePaper(@RequestParam("coursePaperName") final String coursePaperName, @RequestParam("mobileNumber") String mobileNumber) throws Exception {

        studentFacade.unassignStudentFromCoursePaper(coursePaperName, mobileNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> logout(@RequestParam("mobileNumber") final String mobileNumber) throws Exception {

        studentFacade.deleteFCMToken(mobileNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/filterByDeliveryAddress")
    @ResponseStatus(HttpStatus.OK)
    public Page<CoursePaymentInfoResponseDTO> filterByDeliveryAddress() throws Exception {

        return studentFacade.filterByDeliveryAddress();
    }

    @GetMapping("/notifications")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getStudentNotifications(@RequestParam("mobileNumber") final String mobileNumber) throws Exception {

        return studentFacade.getStudentNotifications(mobileNumber);
    }
}

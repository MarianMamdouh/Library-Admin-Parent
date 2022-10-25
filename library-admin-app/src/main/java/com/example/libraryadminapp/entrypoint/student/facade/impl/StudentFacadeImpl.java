package com.example.libraryadminapp.entrypoint.student.facade.impl;

import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.request.StudentLoginRequestModel;
import com.example.libraryadminapp.core.domain.student.response.CoursePaymentInfoResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentLoginResponseModel;
import com.example.libraryadminapp.core.domain.student.service.StudentService;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentCreationRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentLoginRequestDTO;
import com.example.libraryadminapp.entrypoint.student.controller.request.StudentLoginResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.CoursePaymentInfoResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCoursePaperResponseDTO;
import com.example.libraryadminapp.entrypoint.student.controller.response.StudentCourseResponseDTO;
import com.example.libraryadminapp.entrypoint.student.facade.StudentFacade;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;

    @Override
    public void createStudent(final StudentCreationRequestDTO studentCreationRequestDTO) throws IOException {

        final StudentCreationRequestModel studentCreationRequestModel = buildStudentCreationRequestModel(studentCreationRequestDTO);
        studentService.createStudent(studentCreationRequestModel);
    }

    @Override
    public StudentLoginResponseDTO login(final StudentLoginRequestDTO studentLoginDTO) {

        final StudentLoginRequestModel studentLoginModel = buildStudentLoginModel(studentLoginDTO);
        StudentLoginResponseModel studentLoginResponseModel =  studentService.login(studentLoginModel);

        return StudentLoginResponseDTO
                .builder()
                .jwtToken(studentLoginResponseModel.getJwtToken())
                .studentName(studentLoginResponseModel.getStudentName())
                .mobileNumber(studentLoginResponseModel.getMobileNumber())
                .build();
    }

    @Override
    public void verifyStudentOTP(final String otp, final String mobileNumber) throws IOException {

        studentService.verifyStudentOTP(otp, mobileNumber);
    }

    @Override
    public void resendOTP(final String mobileNumber) throws IOException {

        studentService.resendOTP(mobileNumber);
    }

    @Override
    public void setFCMToken(final String fcmToken, final String mobileNumber) throws IOException {

        studentService.setFCMToken(fcmToken, mobileNumber);

    }

    @Override
    public Integer assignCourseToStudent(final String courseName, final String mobileNumber, final Long courseSlotId) {

        return studentService.assignCourseToStudent(courseName, mobileNumber, courseSlotId);
    }

    @Override
    public Integer assignCoursePaperToStudent(final String coursePaperName, final String mobileNumber, final String deliveryAddress) {

        return studentService.assignCoursePaperToStudent(coursePaperName, mobileNumber, deliveryAddress);
    }

    @Override
    public List<StudentCourseResponseDTO> getAllCoursesBookings(final String mobileNumber) {

        return studentService.getAllCoursesBookings(mobileNumber)
                .stream()
                .map(studentCourseResponseModel ->
                    StudentCourseResponseDTO
                            .builder()
                            .courseName(studentCourseResponseModel.getCourseName())
                            .subjectName(studentCourseResponseModel.getSubjectName())
                            .professorName(studentCourseResponseModel.getProfessorName())
                            .pricePerSemester(studentCourseResponseModel.getPricePerSemester())
                            .pricePerMonth(studentCourseResponseModel.getPricePerMonth())
                            .build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<StudentCoursePaperResponseDTO> getAllCoursePapersBookings(final String mobileNumber) {

        return studentService.getAllCoursePapersBookings(mobileNumber)
                .stream()
                .map(studentCourseResponseModel ->
                        StudentCoursePaperResponseDTO
                                .builder()
                                .coursePaperName(studentCourseResponseModel.getCoursePaperName())
                                .subjectName(studentCourseResponseModel.getSubjectName())
                                .professorName(studentCourseResponseModel.getProfessorName())
                                .price(studentCourseResponseModel.getPrice())
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Page<CoursePaymentInfoResponseDTO> getAllCoursesPaymentInfo() {

        return studentService.getAllCoursesPaymentInfo()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                        .mobileNumber(paymentInfoResponseModel.getMobileNumber())
                        .courseName(paymentInfoResponseModel.getCourseName())
                        .build()
                );
    }

    @Override
    public Page<CoursePaymentInfoResponseDTO> getAllCoursePapersPaymentInfo() {

        return studentService.getAllCoursePapersPaymentInfo()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                        .mobileNumber(paymentInfoResponseModel.getMobileNumber())
                        .coursePaperName(paymentInfoResponseModel.getCoursePaperName())
                        .deliveryAddress(paymentInfoResponseModel.getDeliveryAddress())
                        .build()
                );
    }

    @Override
    @Transactional
    public Page<CoursePaymentInfoResponseDTO> findAllByCoursePapersExists() {

        return studentService.findAllByCoursePapersExists()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .mobileNumber(paymentInfoResponseModel.getMobileNumber())
                        .coursePaperName(paymentInfoResponseModel.getCoursePaperName())
                        .build()
                );
    }

    @Override
    @Transactional
    public Page<CoursePaymentInfoResponseDTO> findAllByCourseSlotsExists() {

        return studentService.findAllByCourseSlotsExists()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                        .builder()
                        .mobileNumber(paymentInfoResponseModel.getMobileNumber())
                        .courseName(paymentInfoResponseModel.getCourseName())
                        .build()
                );
    }

    @Override
    public CoursePaymentInfoResponseDTO searchByPaymentInfoNumber(final Integer paymentInfoNumber) {

        final CoursePaymentInfoResponseModel paymentInfoResponseModel = studentService.searchByPaymentInfoNumber(paymentInfoNumber);

        return CoursePaymentInfoResponseDTO
                .builder()
                .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                .mobileNumber(paymentInfoResponseModel.getMobileNumber())
                .coursePaperName(paymentInfoResponseModel.getCoursePaperName())
                .courseName(paymentInfoResponseModel.getCourseName())
                .deliveryAddress(paymentInfoResponseModel.getDeliveryAddress())
                .build();
    }

    @Override
    public void deleteCoursePaperPaymentInfo(final Integer paymentInfoNumber) {

        studentService.deleteCoursePaperPaymentInfo(paymentInfoNumber);
    }

    @Override
    public void deleteCoursePaymentInfo(final Integer paymentInfoNumber) {

        studentService.deleteCoursePaymentInfo(paymentInfoNumber);
    }

    @Override
    public void unassignStudentFromCourse(final String courseName, final String mobileNumber) {

        studentService.unassignStudentFromCourse(courseName, mobileNumber);
    }

    @Override
    public void unassignStudentFromCoursePaper(final String coursePaperName, final String mobileNumber) {

        studentService.unassignStudentFromCoursePaper(coursePaperName, mobileNumber);
    }

    @Override
    public void deleteFCMToken(final String mobileNumber) {

        studentService.deleteFCMToken(mobileNumber);
    }

    @Override
    public Page<CoursePaymentInfoResponseDTO> filterByDeliveryAddress() {

       return studentService.filterByDeliveryAddress()
                .map(paymentInfoResponseModel -> CoursePaymentInfoResponseDTO
                .builder()
                .paymentNumber(paymentInfoResponseModel.getPaymentNumber())
                .mobileNumber(paymentInfoResponseModel.getMobileNumber())
                .coursePaperName(paymentInfoResponseModel.getCoursePaperName())
                .deliveryAddress(paymentInfoResponseModel.getDeliveryAddress())
                .build()
        );
    }

    @Override
    public List<String> getStudentNotifications(final String mobileNumber) {

        return studentService.getStudentNotifications(mobileNumber);
    }

    private StudentCreationRequestModel buildStudentCreationRequestModel(final StudentCreationRequestDTO studentCreationRequestDTO) {

        return StudentCreationRequestModel
                .builder()
                .studentName(studentCreationRequestDTO.getStudentName())
                .mobileNumber(studentCreationRequestDTO.getMobileNumber())
                .password(studentCreationRequestDTO.getPassword())
                .academicYear(studentCreationRequestDTO.getAcademicYear())
                .facultyName(studentCreationRequestDTO.getFacultyName())
                .build();
    }

    private StudentLoginRequestModel buildStudentLoginModel(final StudentLoginRequestDTO loginDTO) {

        return StudentLoginRequestModel
                .builder()
                .mobileNumber(loginDTO.getMobileNumber())
                .password(loginDTO.getPassword())
                .build();
    }
}

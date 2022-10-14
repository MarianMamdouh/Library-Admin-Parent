package com.example.libraryadminapp.core.domain.student.service.impl;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.course.repository.CourseRepository;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.coursepaper.repository.CoursePaperRepository;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.courseslot.repository.CourseSlotRepository;
import com.example.libraryadminapp.core.domain.paymentinfo.entity.PaymentInfo;
import com.example.libraryadminapp.core.domain.paymentinfo.repository.PaymentInfoRepository;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import com.example.libraryadminapp.core.domain.student.repository.StudentRepository;
import com.example.libraryadminapp.core.domain.student.request.StudentCreationRequestModel;
import com.example.libraryadminapp.core.domain.student.response.CoursePaymentInfoResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCourseResponseModel;
import com.example.libraryadminapp.core.domain.student.service.StudentService;
import com.example.libraryadminapp.core.domain.student.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CoursePaperRepository coursePaperRepository;
    private final CourseSlotRepository courseSlotRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    public void createStudent(final StudentCreationRequestModel studentCreationRequestModel) {

        final Student student = buildStudentEntity(studentCreationRequestModel);

        studentRepository.save(student);
    }

    @Override
    public void verifyStudentMobileNumber(final String studentMobileNumber) throws IOException {

        URL url = new URL("https://apis.cequens.com/sms/v1/messages");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImExOTIzYzViNzljOWRkOTgzZTZkZWY3MDg0NjM4YWM4ZTU4ODkzN2ZlYzM2MjM1Y2FmY2YwZGUzM2JjMDYxMzBiMTY3ZTU1MTFlMzk1YTAyMDA4YTgyOTgzYTUwMzJkNmU2MjQxY2UxYmViNzA0OWYwZDU4ZWIyMWUzZjI2MzZiNzQ4ODU5ZGRmMjY1M2ViNjk1OTg0ZGFhN2MyMzA0ZDkiLCJpYXQiOjE2NjU1Nzk3NTMsImV4cCI6MzI0MzQ1OTc1M30.OySNLJ6epmKB2hd7PnHyUvh6y7HRnmjOMKge-tMc02M");
        http.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        Random random = new Random();
        String verificationCode = String.format("%04d", random.nextInt(10000));

        String data = "{\n  \"senderName\": \"SuperOnline\",\n  \"messageType\": \"text\",\n  \"acknowledgement\": 1,\n  \"messageText\": \"hello from aplus,here is your verification code " + verificationCode + "\",\n  \"recipients\": \"" + studentMobileNumber + "\"\n}";

        System.out.println(data);

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }

    @Override
    public Integer assignCourseToStudent(final String courseName, final String studentName, final Long courseSlotId) {

        final Optional<Course> course = courseRepository.findByCourseName(courseName);

        final List<CourseSlot> courseSlots = courseSlotRepository.findAllByCourseId(course.get().getId());


        // should i make a check if number of current bookings = max

        final Student student = studentRepository.findByStudentName(studentName).get();


        if (student.getCourseSlots()
                .stream()
                .map(CourseSlot::getId)
                .anyMatch(courseSlotId::equals)) {

            //throw an error that student is already assigned to this course
//            return ;
        }

        final CourseSlot courseSlot = courseSlots
                .stream()
                .filter(courseSlot1 -> courseSlot1.getId().equals(courseSlotId))
                .findFirst().get();

        student.getCourseSlots().add(courseSlot);
        studentRepository.save(student);

        courseSlot.setCurrentNumberOfBookings(courseSlot.getCurrentNumberOfBookings() + 1);
        courseSlotRepository.save(courseSlot);

        Random random =  new  Random();
        Integer paymentNumber = random.nextInt() + student.getId().intValue();
        PaymentInfo paymentInfo = PaymentInfo.builder()
                .paymentNumber(paymentNumber)
                .student(student)
                .course(course.get())
                .build();

        paymentInfoRepository.save(paymentInfo);

        return paymentNumber;
    }

    @Override
    public Integer assignCoursePaperToStudent(final String coursePaperName, final String studentName) {

        final Optional<CoursePaper> coursePaper = coursePaperRepository.findByName(coursePaperName);

        final Student student = studentRepository.findByStudentName(studentName).get();

        if (student.getCoursePapers()
                .stream()
                .map(CoursePaper::getCoursePaperName)
                .anyMatch(coursePaperName::equals)) {

            // throw an error that student is already assigned to this course.
//            return;
        }

        student.getCoursePapers().add(coursePaper.get());
        studentRepository.save(student);

        Random random =  new  Random();
        Integer paymentNumber = random.nextInt() + student.getId().intValue();
        PaymentInfo paymentInfo = PaymentInfo.builder()
                .paymentNumber(paymentNumber)
                .student(student)
                .coursePaper(coursePaper.get())
                .build();

        paymentInfoRepository.save(paymentInfo);
        return paymentNumber;

    }

    @Override
    public List<StudentCourseResponseModel> getAllCoursesBookings(final String studentName) {

        final Student student = studentRepository.findByStudentName(studentName).get();

        final List<CourseSlot> courseSlots = student.getCourseSlots();

        final List<Course> courses = courseSlots
                .stream()
                .map(CourseSlot::getCourse)
                .collect(Collectors.toList());

        return courses.stream()
                .map(this::buildStudentCourseResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentCoursePaperResponseModel> getAllCoursePapersBookings(final String studentName) {

        final Student student = studentRepository.findByStudentName(studentName).get();

        final List<CoursePaper> coursePapers = student.getCoursePapers();

        return coursePapers.stream()
                .map(this::buildStudentCoursePaperResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CoursePaymentInfoResponseModel> getAllCoursesPaymentInfo() {

        return paymentInfoRepository.findAllByCourseIsNotNull()
                .map(paymentInfo ->
                        CoursePaymentInfoResponseModel.builder()
                                .studentName(paymentInfo.getStudent().getStudentName())
                                .courseName(Objects.nonNull(paymentInfo.getCourse())? paymentInfo.getCourse().getCourseName(): null)
                                .paymentNumber(paymentInfo.getPaymentNumber())
                                .build()
        );
    }

    @Override
    public Page<CoursePaymentInfoResponseModel> getAllCoursePapersPaymentInfo() {

        return paymentInfoRepository.findAllByCoursePaperIsNotNull()
                .map(paymentInfo ->
                        CoursePaymentInfoResponseModel.builder()
                                .studentName(paymentInfo.getStudent().getStudentName())
                                .coursePaperName(paymentInfo.getCoursePaper().getCoursePaperName())
                                .paymentNumber(paymentInfo.getPaymentNumber())
                                .build()
                );
    }

    @Override
    public Page<CoursePaymentInfoResponseModel> searchByPaymentInfoNumber(final Integer paymentInfoNumber) {

        return paymentInfoRepository.findByPaymentNumber(paymentInfoNumber)
                .map(paymentInfo ->
                        CoursePaymentInfoResponseModel.builder()
                                .studentName(paymentInfo.getStudent().getStudentName())
                                .coursePaperName(Objects.nonNull(paymentInfo.getCoursePaper())? paymentInfo.getCoursePaper().getCoursePaperName(): null)
                                .courseName(Objects.nonNull(paymentInfo.getCourse())? paymentInfo.getCourse().getCourseName(): null)
                                .paymentNumber(paymentInfo.getPaymentNumber())
                                .build()
                );    }

    private StudentCourseResponseModel buildStudentCourseResponseModel(final Course course) {

        return StudentCourseResponseModel
                .builder()
                .courseName(course.getCourseName())
                .professorName(course.getProfessorName())
                .subjectName(course.getSubjectName())
                .pricePerMonth(course.getPricePerMonth())
                .pricePerSemester(course.getPricePerSemester())
//                .maxNumberOfBookings(course.getMaxNumberOfBookings())
                .build();
    }

    private StudentCoursePaperResponseModel buildStudentCoursePaperResponseModel(final CoursePaper coursePaper) {

        return StudentCoursePaperResponseModel
                .builder()
                .coursePaperName(coursePaper.getCoursePaperName())
                .professorName(coursePaper.getProfessorName())
                .subjectName(coursePaper.getSubjectName())
                .price(coursePaper.getPrice())
                .build();
    }


    private Student buildStudentEntity(final StudentCreationRequestModel studentCreationRequestModel) {

        return Student
                .builder()
                .studentName(studentCreationRequestModel.getStudentName())
                .mobileNumber(studentCreationRequestModel.getMobileNumber())
                .academicYear(studentCreationRequestModel.getAcademicYear())
                .facultyName(studentCreationRequestModel.getFacultyName())
                .status(Status.ACTIVE)
                .build();
    }
}

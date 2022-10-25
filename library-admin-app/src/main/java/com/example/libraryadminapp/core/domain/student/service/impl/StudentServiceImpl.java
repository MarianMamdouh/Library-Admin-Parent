package com.example.libraryadminapp.core.domain.student.service.impl;

import com.example.libraryadminapp.core.domain.auth.userdetails.service.impl.UserDetailsImpl;
import com.example.libraryadminapp.core.domain.auth.utils.JwtUtils;
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
import com.example.libraryadminapp.core.domain.student.request.StudentLoginRequestModel;
import com.example.libraryadminapp.core.domain.student.response.CoursePaymentInfoResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCoursePaperResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentCourseResponseModel;
import com.example.libraryadminapp.core.domain.student.response.StudentLoginResponseModel;
import com.example.libraryadminapp.core.domain.student.service.StudentService;
import com.example.libraryadminapp.core.domain.student.utils.Status;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CoursePaperRepository coursePaperRepository;
    private final CourseSlotRepository courseSlotRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public void createStudent(final StudentCreationRequestModel studentCreationRequestModel) throws IOException {

        if (studentRepository.existsByMobileNumber(studentCreationRequestModel.getMobileNumber())) {

            throw new IllegalArgumentException("Mobile number " + studentCreationRequestModel.getMobileNumber() + " already exists");

        }

        validateMobileNumber(studentCreationRequestModel.getMobileNumber());

        final Student student = buildStudentEntity(studentCreationRequestModel);

        final String otp = verifyStudentMobileNumber(student.getMobileNumber());

        student.setOtp(otp);

        studentRepository.save(student);

    }

    private void validateMobileNumber(final String mobileNumber) {

        final Pattern pattern = Pattern.compile("^01[0125][0-9]{8}$");
        final Matcher matcher = pattern.matcher(mobileNumber);

        if (!matcher.matches()) {

            throw new IllegalArgumentException("Mobile number format is incorrect");
        }
    }

    @Override
    public StudentLoginResponseModel login(final StudentLoginRequestModel studentLoginModel) {

        final Optional<Student> studentOptional = studentRepository.findByMobileNumber(studentLoginModel.getMobileNumber());
        String username = "";

        if (studentOptional.isPresent()) {
            if (Status.INACTIVE.equals(studentOptional.get().getStatus())) {

                throw new IllegalArgumentException("Mobile number hasn't been activated yet!");
            }
            username = studentOptional.get().getStudentName();
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, studentLoginModel.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        return StudentLoginResponseModel
                .builder()
                .jwtToken(jwt)
                .mobileNumber(userDetails.getMobileNumber())
                .studentName(userDetails.getUsername()).build();
    }

    @Override
    public void verifyStudentOTP(String otp, String mobileNumber) throws IOException {

        if (!studentRepository.existsByMobileNumberAndOTP(mobileNumber, otp)) {

            throw new IllegalArgumentException("OTP is incorrect, Please recheck and try again!");
        }

        final Student student = studentRepository.findByMobileNumber(mobileNumber).get();

        student.setStatus(Status.ACTIVE);
        studentRepository.save(student);
    }

    @Override
    public void resendOTP(final String mobileNumber) throws IOException {

        final Student student = findByMobileNumber(mobileNumber);

        if (Status.ACTIVE.equals(student.getStatus())) {

            throw new IllegalArgumentException("Student with mobile number" + mobileNumber + " is already activated ");
        }

        final String otp = verifyStudentMobileNumber(student.getMobileNumber());

        student.setOtp(otp);

        studentRepository.save(student);
    }

    @Override
    public void setFCMToken(final String fcmToken, final String mobileNumber) throws IOException {

        final Student student = studentRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new IllegalArgumentException("Student with mobile number " + mobileNumber + " can't be found")
        );

        student.setFcmToken(fcmToken);

        studentRepository.save(student);
    }

    public String verifyStudentMobileNumber(final String studentMobileNumber) throws IOException {

        final URL url = new URL("https://apis.cequens.com/sms/v1/messages");
        final HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImExOTIzYzViNzljOWRkOTgzZTZkZWY3MDg0NjM4YWM4ZTU4ODkzN2ZlYzM2MjM1Y2FmY2YwZGUzM2JjMDYxMzBiMTY3ZTU1MTFlMzk1YTAyMDA4YTgyOTgzYTUwMzJkNmU2MjQxY2UxYmViNzA0OWYwZDU4ZWIyMWUzZjI2MzZiNzQ4ODU5ZGRmMjY1M2ViNjk1OTg0ZGFhN2MyMzA0ZDkiLCJpYXQiOjE2NjU1Nzk3NTMsImV4cCI6MzI0MzQ1OTc1M30.OySNLJ6epmKB2hd7PnHyUvh6y7HRnmjOMKge-tMc02M");
        http.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        final Random random = new Random();
        final String verificationCode = String.format("%04d", random.nextInt(10000));

        final String data = "{\n  \"senderName\": \"SuperOnline\",\n  \"messageType\": \"text\",\n  \"acknowledgement\": 1,\n  \"messageText\": \"أهلا بك في مكتبة A Plus, كود التفعيل:  " + verificationCode + "\",\n  \"recipients\": \"" + studentMobileNumber + "\"\n}";

        final byte[] out = data.getBytes(StandardCharsets.UTF_8);

        final OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();

        return verificationCode;
    }

    @Override
    public Integer assignCourseToStudent(final String courseName, final String mobileNumber, final Long courseSlotId) {

        final Course course = courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("Course " + courseName + "can't be found"));

        final Student student = findByMobileNumber(mobileNumber);

        final List<CourseSlot> courseSlots = courseSlotRepository.findAllByCourseId(course.getId());

        if (student.getCourseSlots()
                .stream()
                .anyMatch(courseSlots::contains)) {

            throw new IllegalArgumentException("Student with mobile number " + mobileNumber + " is already assigned to this course " + courseName);
        }

        final CourseSlot courseSlot = courseSlots
                .stream()
                .filter(courseSlot1 -> courseSlot1.getId().equals(courseSlotId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selected Course Slot can't be found for course " + courseName));

        if (Objects.equals(courseSlot.getMaximumNumberOfBookings(), courseSlot.getCurrentNumberOfBookings())) {

            throw new IllegalArgumentException("Selected Course Slot for course " + courseName + " is full");
        }

        student.getCourseSlots().add(courseSlot);
        studentRepository.save(student);

        courseSlot.setCurrentNumberOfBookings(courseSlot.getCurrentNumberOfBookings() + 1);
        courseSlotRepository.save(courseSlot);

        final Random random = new Random();
        final Integer paymentNumber = random.nextInt() & Integer.MAX_VALUE;
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .paymentNumber(paymentNumber)
                .student(student)
                .course(course)
                .build();

        paymentInfoRepository.save(paymentInfo);

        return paymentNumber;
    }

    @Override
    public Integer assignCoursePaperToStudent(final String coursePaperName, final String mobileNumber, final String deliveryAddress) {

        final CoursePaper coursePaper = coursePaperRepository.findByName(coursePaperName)
                .orElseThrow(() -> new IllegalArgumentException("Course Paper " + coursePaperName + " can't be found"));

        final Student student = findByMobileNumber(mobileNumber);

//        if (student.getCoursePapers()
//                .stream()
//                .map(CoursePaper::getCoursePaperName)
//                .anyMatch(coursePaperName::equals)) {
//
//            throw new IllegalArgumentException("Student with mobile number " + mobileNumber + " is already assigned to this course paper " + coursePaperName);
//        }

        student.getCoursePapers().add(coursePaper);
        studentRepository.save(student);

        final Random random = new Random();
        final Integer paymentNumber = random.nextInt() & Integer.MAX_VALUE;
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .paymentNumber(paymentNumber)
                .student(student)
                .coursePaper(coursePaper)
                .deliveryAddress(deliveryAddress)
                .build();

        paymentInfoRepository.save(paymentInfo);
        return paymentNumber;
    }

    @Override
    public List<StudentCourseResponseModel> getAllCoursesBookings(final String mobileNumber) {

        final Student student = findByMobileNumber(mobileNumber);

        //todo
        final List<CourseSlot> courseSlots = student.getCourseSlots();

        final List<Course> courses = courseSlots
                .stream()
                .map(CourseSlot::getCourse)
                .collect(Collectors.toList());

        return courses.stream().map(this::buildStudentCourseResponseModel).collect(Collectors.toList());
    }

    @Override
    public List<StudentCoursePaperResponseModel> getAllCoursePapersBookings(final String mobileNumber) {

        final Student student = findByMobileNumber(mobileNumber);

        final List<CoursePaper> coursePapers = student.getCoursePapers();

        return coursePapers
                .stream()
                .map(this::buildStudentCoursePaperResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CoursePaymentInfoResponseModel> getAllCoursesPaymentInfo() {

        return paymentInfoRepository.findAllByCourseIsNotNull()
                .map(paymentInfo ->
                        CoursePaymentInfoResponseModel.builder()
                                .mobileNumber(paymentInfo.getStudent().getMobileNumber())
                                .courseName(paymentInfo.getCourse().getCourseName())
                                .paymentNumber(paymentInfo.getPaymentNumber())
                                .build()
                );
    }

    @Override
    public Page<CoursePaymentInfoResponseModel> getAllCoursePapersPaymentInfo() {

        return paymentInfoRepository.findAllByCoursePaperIsNotNull()
                .map(paymentInfo ->
                        CoursePaymentInfoResponseModel.builder()
                                .mobileNumber(paymentInfo.getStudent().getMobileNumber())
                                .coursePaperName(paymentInfo.getCoursePaper().getCoursePaperName())
                                .paymentNumber(paymentInfo.getPaymentNumber())
                                .deliveryAddress(paymentInfo.getDeliveryAddress())
                                .build()
                );
    }

    @Override
    public CoursePaymentInfoResponseModel searchByPaymentInfoNumber(final Integer paymentInfoNumber) {

        PaymentInfo paymentInfo = paymentInfoRepository.findByPaymentNumber(paymentInfoNumber)
                .orElseThrow(() -> new IllegalArgumentException("Payment info with number " + paymentInfoNumber + " can't be found"));

        return CoursePaymentInfoResponseModel.builder()
                .mobileNumber(paymentInfo.getStudent().getStudentName())
                .coursePaperName(Objects.nonNull(paymentInfo.getCoursePaper()) ? paymentInfo.getCoursePaper().getCoursePaperName() : null)
                .courseName(Objects.nonNull(paymentInfo.getCourse()) ? paymentInfo.getCourse().getCourseName() : null)
                .paymentNumber(paymentInfo.getPaymentNumber())
                .deliveryAddress(paymentInfo.getDeliveryAddress())
                .build();

    }

    @Override
    @Transactional
    public void deleteCoursePaperPaymentInfo(final Integer paymentInfoNumber) {

        paymentInfoRepository.findByPaymentNumber(paymentInfoNumber)
                .orElseThrow(() -> new IllegalArgumentException("Payment info with number " + paymentInfoNumber + " can't be found"));

        paymentInfoRepository.deleteByPaymentNumber(paymentInfoNumber);
    }

    @Override
    @Transactional
    public void unassignStudentFromCoursePaper(final String coursePaperName, final String mobileNumber) {

        final CoursePaper coursePaper = coursePaperRepository.findByName(coursePaperName)
                .orElseThrow(() -> new IllegalArgumentException("Course Paper with name " + coursePaperName + " can't be found"));

        final Student student = findByMobileNumber(mobileNumber);

        student.getCoursePapers().remove(coursePaper);

        studentRepository.save(student);

        final List<PaymentInfo> paymentInfos = paymentInfoRepository.findAllByCoursePaperNameAndMobileNumber(coursePaperName, mobileNumber);

        if(!paymentInfos.isEmpty()) {

            paymentInfoRepository.deleteByPaymentNumber(paymentInfos.get(0).getPaymentNumber());
        }
    }

    @Override
    public void deleteCoursePaymentInfo(final Integer paymentInfoNumber) {

        paymentInfoRepository.findByPaymentNumber(paymentInfoNumber)
                .orElseThrow(() -> new IllegalArgumentException("Payment info with number " + paymentInfoNumber + " can't be found"));

        paymentInfoRepository.deleteByPaymentNumber(paymentInfoNumber);
    }

    @Override
    public void deleteFCMToken(final String mobileNumber) {

        final Student student = findByMobileNumber(mobileNumber);

        student.setFcmToken(null);

        studentRepository.save(student);
    }

    @Override
    public Page<CoursePaymentInfoResponseModel> filterByDeliveryAddress() {

        Page<PaymentInfo> paymentInfos = paymentInfoRepository.findAllByDeliveryAddressIsNotNullAndDeliveryAddressNotEquals();

        return paymentInfos.map(paymentInfo ->
                CoursePaymentInfoResponseModel.builder()
                        .mobileNumber(paymentInfo.getStudent().getStudentName())
                        .coursePaperName(paymentInfo.getCoursePaper().getCoursePaperName())
                        .paymentNumber(paymentInfo.getPaymentNumber())
                        .deliveryAddress(paymentInfo.getDeliveryAddress())
                        .build()
        );
    }

    @Override
    public List<String> getStudentNotifications(final String mobileNumber) {

        final Student student = findByMobileNumber(mobileNumber);

        return student.getNotification().stream().limit(50).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Page<CoursePaymentInfoResponseModel> findAllByCoursePapersExists() {

        final List<Student> students = studentRepository.findAll()
                .stream()
                .filter(student -> Objects.nonNull(student.getCoursePapers()))
                .collect(Collectors.toList());

        List<CoursePaymentInfoResponseModel> coursePaymentInfoResponseModels = students
                .stream()
                .map(student -> student.getCoursePapers()
                        .stream()
                        .map(coursePaper -> CoursePaymentInfoResponseModel.builder()
                                .mobileNumber(student.getMobileNumber())
                                .coursePaperName(coursePaper.getCoursePaperName())
                                .build())
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return new PageImpl<>(coursePaymentInfoResponseModels);
    }

    @Override
    @Transactional
    public Page<CoursePaymentInfoResponseModel> findAllByCourseSlotsExists() {

        Page<Student> students = studentRepository.findAllByCourseSlotsExists();

        List<CoursePaymentInfoResponseModel> coursePaymentInfoResponseModels = students
                .stream()
                .map(student -> student.getCoursePapers()
                        .stream()
                        .map(coursePaper -> CoursePaymentInfoResponseModel.builder()
                                .mobileNumber(student.getMobileNumber())
                                .coursePaperName(coursePaper.getCoursePaperName())
                                .build())
                        .distinct()
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return new PageImpl<>(coursePaymentInfoResponseModels);
    }

    @Override
    public void unassignStudentFromCourse(final String courseName, final String mobileNumber) {

        final Student student = findByMobileNumber(mobileNumber);

        final Course course = courseRepository.findByCourseName(courseName)
                .orElseThrow(() -> new IllegalArgumentException("Course with name " + courseName + "couldn't be found"));

        final List<CourseSlot> courseSlots = courseSlotRepository.findAllByCourseId(course.getId());

        final CourseSlot assignedCourseSlot = courseSlots
                .stream()
                .filter(c -> new ArrayList<>(student.getCourseSlots()).contains(c))
                .findAny().get();

        assignedCourseSlot.setCurrentNumberOfBookings(assignedCourseSlot.getCurrentNumberOfBookings() - 1);

        student.getCourseSlots().remove(assignedCourseSlot);

        studentRepository.save(student);

        final PaymentInfo paymentInfo = paymentInfoRepository.findByCourseNameAndMobileNumber(courseName, mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Payment info with course name " + courseName + " and mobile number " + mobileNumber + " can't be found"));

        paymentInfoRepository.deleteByPaymentNumber(paymentInfo.getPaymentNumber());
    }

    private StudentCourseResponseModel buildStudentCourseResponseModel(final Course course) {

        return StudentCourseResponseModel
                .builder()
                .courseName(course.getCourseName())
                .professorName(course.getProfessorName())
                .subjectName(course.getSubjectName())
                .pricePerMonth(course.getPricePerMonth())
                .pricePerSemester(course.getPricePerSemester())
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
                .status(Status.INACTIVE)
                .password(encoder.encode(studentCreationRequestModel.getPassword()))
                .build();
    }

    private Student findByMobileNumber(final String mobileNumber) {

        return studentRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Student with mobile number " + mobileNumber + "can't be found"));
    }
}

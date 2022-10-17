package com.example.libraryadminapp.core.domain.student.entity;

import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.student.utils.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String academicYear;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String facultyName;

    @Column(nullable = false)
    private Status status;

    private String otp;

    private String fcmToken;

    @ManyToMany
    private List<CoursePaper> coursePapers;

    @ManyToMany
    private List<CourseSlot> courseSlots;
}

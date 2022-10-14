package com.example.libraryadminapp.core.domain.student.entity;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.student.utils.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(nullable = false)
    private String facultyName;

    @Column(nullable = false)
    private Status status;

    @ManyToMany
    private List<CoursePaper> coursePapers;

    @ManyToMany
    private List<CourseSlot> courseSlots;
}

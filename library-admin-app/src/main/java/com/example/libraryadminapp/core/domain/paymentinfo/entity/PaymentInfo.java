package com.example.libraryadminapp.core.domain.paymentinfo.entity;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.student.entity.Student;
import lombok.*;

import javax.persistence.*;


@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer paymentNumber;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "course_paper_id")
    private CoursePaper coursePaper;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

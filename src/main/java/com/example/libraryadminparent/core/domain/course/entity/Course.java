package com.example.libraryadminparent.core.domain.course.entity;

import com.example.libraryadminparent.core.domain.professor.Professor;
import lombok.Builder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private BigDecimal pricePerSemester;

    @Column(nullable = false)
    private BigDecimal pricePerMonth;

    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false)
    private Integer maxNumberOfBookings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
}

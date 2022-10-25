package com.example.libraryadminapp.core.domain.coursepaper.entity;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.course.entity.Course;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoursePaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String coursePaperName;

    @Column(nullable = false)
    private String subjectName;

    @Column(nullable = false)
    private String professorName;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    @OneToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}

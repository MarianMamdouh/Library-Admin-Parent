package com.example.libraryadminapp.core.domain.course.entity;

import com.example.libraryadminapp.core.domain.academicyear.entity.AcademicYear;
import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.faculty.entity.Faculty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String professorName;

    @Column(nullable = false)
    private BigDecimal pricePerSemester;

    @Column(nullable = false)
    private BigDecimal pricePerMonth;

    @Column(nullable = false)
    private String subjectName;

    @OneToMany(mappedBy = "id")
    private List<CourseSlot> courseSlots;

    @ManyToMany
    private List<CoursePaper> coursePapers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "academic_year_id")
    private AcademicYear academicYear;

    @OneToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}

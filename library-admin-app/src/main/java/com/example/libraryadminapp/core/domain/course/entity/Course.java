package com.example.libraryadminapp.core.domain.course.entity;

import com.example.libraryadminapp.core.domain.coursepaper.CoursePaper;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @Column(nullable = false)
    private Integer maxNumberOfBookings;

    @OneToMany(mappedBy = "id", orphanRemoval = true)
    private List<CoursePaper> coursePapers = new ArrayList<>();
}

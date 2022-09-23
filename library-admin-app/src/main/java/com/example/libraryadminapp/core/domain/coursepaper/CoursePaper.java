package com.example.libraryadminapp.core.domain.coursepaper;

import com.example.libraryadminapp.core.domain.course.entity.Course;
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

    private Integer numOfCopies;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}

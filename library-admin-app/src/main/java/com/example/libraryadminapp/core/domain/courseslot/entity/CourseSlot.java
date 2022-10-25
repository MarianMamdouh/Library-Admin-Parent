package com.example.libraryadminapp.core.domain.courseslot.entity;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Integer maximumNumberOfBookings;

    @Column(nullable = false)
    private Integer currentNumberOfBookings = 0;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


}

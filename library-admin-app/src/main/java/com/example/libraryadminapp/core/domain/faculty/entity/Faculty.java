package com.example.libraryadminapp.core.domain.faculty.entity;

import com.example.libraryadminapp.core.domain.course.entity.Course;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}

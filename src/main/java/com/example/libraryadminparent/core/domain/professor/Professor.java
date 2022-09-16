package com.example.libraryadminparent.core.domain.professor;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Builder
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
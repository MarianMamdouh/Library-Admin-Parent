package com.example.libraryadminapp.infrastructure.database.courseslot.impl;


import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import com.example.libraryadminapp.core.domain.courseslot.repository.CourseSlotRepository;
import com.example.libraryadminapp.infrastructure.database.courseslot.CourseSlotJpaRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class CourseSlotRepositoryImpl implements CourseSlotRepository {

    private final CourseSlotJpaRepository courseSlotJpaRepository;

    @Override
    public List<CourseSlot> findAllByCourseId(final long courseId) {

        return courseSlotJpaRepository.findAllByCourseId(courseId);
    }

    @Override
    public List<CourseSlot> saveAll(final List<CourseSlot> courseSlots) {

        return courseSlotJpaRepository.saveAll(courseSlots);
    }

    @Override
    public void deleteById(final Long courseSlotId) {

        courseSlotJpaRepository.deleteById(courseSlotId);
    }

    @Override
    public Optional<CourseSlot> findById(final Long courseSlotId) {

      return courseSlotJpaRepository.findById(courseSlotId);
    }

    @Override
    public void save(final CourseSlot courseSlot) {

        courseSlotJpaRepository.save(courseSlot);
    }


}

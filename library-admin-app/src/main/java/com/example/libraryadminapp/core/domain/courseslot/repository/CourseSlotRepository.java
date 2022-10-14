package com.example.libraryadminapp.core.domain.courseslot.repository;

import com.example.libraryadminapp.core.domain.coursepaper.entity.CoursePaper;
import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;

import java.util.List;
import java.util.Optional;

public interface CourseSlotRepository {

    List<CourseSlot> findAllByCourseId(long courseId);

    void deleteById(final Long courseSlotId);

    List<CourseSlot> saveAll(List<CourseSlot> courseSlots);

    Optional<CourseSlot> findById(final Long courseSlotId);

    void save(CourseSlot courseSlot);


}

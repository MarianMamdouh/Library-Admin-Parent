package com.example.libraryadminapp.infrastructure.database.courseslot;

import com.example.libraryadminapp.core.domain.courseslot.entity.CourseSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSlotJpaRepository extends JpaRepository<CourseSlot, Long> {

    List<CourseSlot> findAllByCourseId(long id);

}

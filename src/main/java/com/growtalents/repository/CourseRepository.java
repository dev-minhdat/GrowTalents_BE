package com.growtalents.repository;

import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import com.growtalents.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByNameContainingIgnoreCase(String keyword);
    List<Course> findByStatus(CourseStatus status);
    List<Course> findByType(CourseType type);
    List<Course> findByStatusAndType(CourseStatus status, CourseType type);
}

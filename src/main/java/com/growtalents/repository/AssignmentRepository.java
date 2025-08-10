package com.growtalents.repository;

import com.growtalents.model.Assignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {

    List<Assignment> findByLesson_LessonId(String lessonId);

    @Query("""
        select distinct a
        from Assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus s
        join TeacherCourse tc on tc.course = s.course
        where tc.teacher.userId = :teacherId
        order by a.createdAt desc
    """)
    List<Assignment> findByTeacherIdOrderByCreatedAtDesc(@Param("teacherId") String teacherId);
}

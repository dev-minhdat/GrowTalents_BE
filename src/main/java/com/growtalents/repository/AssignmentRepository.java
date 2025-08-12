package com.growtalents.repository;

import com.growtalents.model.Assignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {

    // Code từ TuDat
    @Query("SELECT a FROM Assignment a " +
           "JOIN FETCH a.course " +
           "WHERE a.course.courseId = :courseId " +
           "ORDER BY a.assignmentId DESC")
    List<Assignment> findByCourseIdOrderByCreatedDateDesc(@Param("courseId") String courseId);

    @Query("SELECT a FROM Assignment a " +
           "JOIN FETCH a.course " +
           "WHERE a.course.courseId IN (" +
           "    SELECT sc.course.courseId FROM StudentCourse sc " +
           "    WHERE sc.student.userId = :studentId " +
           "    AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED" +
           ") " +
           "ORDER BY a.assignmentId DESC")
    List<Assignment> findStudentAssignmentsOrderByNewest(@Param("studentId") String studentId);

    @Query("SELECT COUNT(a) FROM Assignment a " +
           "WHERE a.course.courseId IN (" +
           "    SELECT sc.course.courseId FROM StudentCourse sc " +
           "    WHERE sc.student.userId = :studentId " +
           "    AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED" +
           ")")
    int countTotalAssignmentsByStudentId(@Param("studentId") String studentId);

    // Code từ master
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

package com.growtalents.repository;

import com.growtalents.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    
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
}

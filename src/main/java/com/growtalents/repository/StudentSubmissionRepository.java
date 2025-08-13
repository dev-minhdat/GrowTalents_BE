package com.growtalents.repository;

import com.growtalents.model.StudentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSubmissionRepository extends JpaRepository<StudentSubmission, String> {
    
    @Query("SELECT ss FROM StudentSubmission ss " +
           "WHERE ss.student.userId = :studentId " +
           "AND ss.assignment.assignmentId = :assignmentId")
    Optional<StudentSubmission> findByStudentIdAndAssignmentId(
            @Param("studentId") String studentId, 
            @Param("assignmentId") String assignmentId);
    
    @Query("SELECT ss FROM StudentSubmission ss " +
           "WHERE ss.student.userId = :studentId " +
           "ORDER BY ss.submittedAt DESC")
    List<StudentSubmission> findByStudentIdOrderBySubmittedAtDesc(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(ss) FROM StudentSubmission ss " +
           "JOIN ss.assignment a " +
           "JOIN a.lesson l " +
           "JOIN l.chapter ch " +
           "JOIN ch.syllabus s " +
           "JOIN s.course c " +
           "WHERE ss.student.userId = :studentId " +
           "AND c.courseId IN (" +
           "    SELECT sc.course.courseId FROM StudentCourse sc " +
           "    WHERE sc.student.userId = :studentId " +
           "    AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED" +
           ")")
    int countCompletedAssignmentsByStudentId(@Param("studentId") String studentId);
}

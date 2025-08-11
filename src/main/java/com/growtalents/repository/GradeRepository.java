package com.growtalents.repository;

import com.growtalents.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    
    @Query("SELECT g FROM Grade g " +
           "JOIN FETCH g.assignment " +
           "JOIN FETCH g.course " +
           "WHERE g.student.userId = :studentId " +
           "AND g.assignment IS NOT NULL " +
           "AND g.comment IS NOT NULL " +
           "ORDER BY g.gradeId DESC")
    List<Grade> findStudentGradeCommentsOrderByNewest(@Param("studentId") String studentId);
    
    @Query("SELECT AVG(g.score) FROM Grade g " +
           "WHERE g.student.userId = :studentId " +
           "AND g.score IS NOT NULL")
    Float calculateAverageScoreByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT g FROM Grade g " +
           "JOIN FETCH g.assignment " +
           "JOIN FETCH g.course " +
           "WHERE g.student.userId = :studentId " +
           "AND g.assignment.assignmentId = :assignmentId")
    List<Grade> findByStudentIdAndAssignmentId(
            @Param("studentId") String studentId, 
            @Param("assignmentId") String assignmentId);
}

package com.growtalents.repository;

import com.growtalents.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {

    // Lấy grade có comment của student, mới nhất trước
    @Query("""
        select g
        from Grade g
        join g.assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where g.student.userId = :studentId
          and g.comment is not null
        order by g.gradeId desc
    """)
    List<Grade> findStudentGradeCommentsOrderByNewest(@Param("studentId") String studentId);

    // Trung bình điểm của student
    @Query("""
        select avg(g.score)
        from Grade g
        where g.student.userId = :studentId
          and g.score is not null
    """)
    Float calculateAverageScoreByStudentId(@Param("studentId") String studentId);

    // Lấy grade theo student + assignment
    @Query("""
        select g
        from Grade g
        join g.assignment a
        where g.student.userId = :studentId
          and a.assignmentId = :assignmentId
    """)
    List<Grade> findByStudentIdAndAssignmentId(
            @Param("studentId") String studentId,
            @Param("assignmentId") String assignmentId
    );
    Long countByAssignment_AssignmentId(String assignmentId);

}

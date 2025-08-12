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

    // OK: lấy submission theo student + assignment
    @Query("""
        select ss
        from StudentSubmission ss
        where ss.student.userId = :studentId
          and ss.assignment.assignmentId = :assignmentId
    """)
    Optional<StudentSubmission> findByStudentIdAndAssignmentId(
            @Param("studentId") String studentId,
            @Param("assignmentId") String assignmentId
    );

    // OK: list submission của 1 student mới nhất trước
    @Query("""
        select ss
        from StudentSubmission ss
        where ss.student.userId = :studentId
        order by ss.submittedAt desc
    """)
    List<StudentSubmission> findByStudentIdOrderBySubmittedAtDesc(@Param("studentId") String studentId);

    // FIX: đếm số bài đã nộp của student trong các khóa mà student ENROLLED
    // (nếu cho phép nộp nhiều lần/assignment, nên dùng COUNT DISTINCT)
    @Query("""
        select count(distinct ss.assignment.assignmentId)
        from StudentSubmission ss
        join ss.assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where ss.student.userId = :studentId
          and exists (
            select 1
            from StudentCourse sc
            where sc.course = c
              and sc.student.userId = :studentId
              and sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED
          )
    """)
    int countCompletedAssignmentsByStudentId(@Param("studentId") String studentId);
}

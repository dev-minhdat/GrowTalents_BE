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

    // Lấy submission theo student + assignment
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

    // List submission của 1 student mới nhất trước
    @Query("""
        select ss
        from StudentSubmission ss
        where ss.student.userId = :studentId
        order by ss.submittedAt desc
    """)
    List<StudentSubmission> findByStudentIdOrderBySubmittedAtDesc(@Param("studentId") String studentId);

    // Đếm số bài đã nộp của student trong các khóa đã ENROLLED (distinct để tránh nộp nhiều lần)
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
    long countCompletedAssignmentsByStudentId(@Param("studentId") String studentId);

    // Kiểm tra đã nộp hay chưa cho 1 assignment cụ thể
    boolean existsByAssignment_AssignmentIdAndStudent_UserId(String assignmentId, String studentId);

    // Danh sách assignmentId mà học sinh đã nộp (trong 1 khóa cụ thể), sắp theo lần nộp mới nhất
    @Query("""
        select a.assignmentId
        from StudentSubmission ss
        join ss.assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where ss.student.userId = :studentId
          and c.courseId = :courseId
        group by a.assignmentId
        order by max(ss.submittedAt) desc
    """)
    List<String> findSubmittedAssignmentIdsInCourse(
            @Param("studentId") String studentId,
            @Param("courseId") String courseId
    );

    // Số lượng bài đã nộp trong 1 khóa (distinct để tránh nộp nhiều lần)
    @Query("""
        select count(distinct a.assignmentId)
        from StudentSubmission ss
        join ss.assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where ss.student.userId = :studentId
          and c.courseId = :courseId
    """)
    long countSubmittedAssignmentsInCourse(
            @Param("studentId") String studentId,
            @Param("courseId") String courseId
    );

    // Danh sách assignmentId đã nộp của học sinh trên tất cả các khóa, sắp theo lần nộp mới nhất
    @Query("""
        select a.assignmentId
        from StudentSubmission ss
        join ss.assignment a
        where ss.student.userId = :studentId
        group by a.assignmentId
        order by max(ss.submittedAt) desc
    """)
    List<String> findSubmittedAssignmentIdsForStudent(@Param("studentId") String studentId);
}

package com.growtalents.repository;

import com.growtalents.model.Assignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {

    // --- FIX TuDat: Assignment không có field 'course' ---
    @Query("""
        select a
        from Assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where c.courseId = :courseId
        order by a.createdAt desc
    """)
    List<Assignment> findByCourseIdOrderByCreatedDateDesc(@Param("courseId") String courseId);

    @Query("""
        select distinct a
        from Assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where exists (
            select 1
            from StudentCourse sc
            where sc.course = c
              and sc.student.userId = :studentId
              and sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED
        )
        order by a.createdAt desc
    """)
    List<Assignment> findStudentAssignmentsOrderByNewest(@Param("studentId") String studentId);

    @Query("""
        select count(a)
        from Assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where exists (
            select 1
            from StudentCourse sc
            where sc.course = c
              and sc.student.userId = :studentId
              and sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED
        )
    """)
    int countTotalAssignmentsByStudentId(@Param("studentId") String studentId);
    // (Gợi ý: dùng long thay int nếu muốn an toàn hơn với số lượng lớn)

    // --- Code từ master (giữ nguyên) ---
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

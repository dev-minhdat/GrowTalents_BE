package com.growtalents.repository;

import com.growtalents.model.Assignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {

    // Lấy tất cả assignment của 1 course
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

    // Lấy tất cả assignment trong các course mà student đã ENROLLED
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

    // Đếm tổng số assignment trong các course mà student đã ENROLLED
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

    List<Assignment> findByLesson_LessonId(String lessonId);

    // Lấy assignment của 1 teacher
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

    // A) Assignments của 1 course
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
    List<Assignment> findAssignmentsInCourse(@Param("courseId") String courseId);

    // B) Assignments của 1 course nếu student đã ENROLLED
    @Query("""
        select a
        from Assignment a
        join a.lesson l
        join l.chapter ch
        join ch.syllabus sy
        join sy.course c
        where c.courseId = :courseId
          and exists (
            select 1 from StudentCourse sc
            where sc.course = c
              and sc.student.userId = :studentId
              and sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED
          )
        order by a.createdAt desc
    """)
    List<Assignment> findAssignmentsForStudentInCourse(
            @Param("studentId") String studentId,
            @Param("courseId") String courseId
    );

    // C) Assignments của tất cả course mà student đã ENROLLED
    @Query("""
        select a
        from StudentCourse sc
        join sc.course c
        join Syllabus sy on sy.course = c
        join Chapter ch on ch.syllabus = sy
        join Lesson l on l.chapter = ch
        join Assignment a on a.lesson = l
        where sc.student.userId = :studentId
          and sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED
        order by c.name asc, a.createdAt desc
    """)
    List<Assignment> findAssignmentsForStudentAcrossCourses(@Param("studentId") String studentId);
}

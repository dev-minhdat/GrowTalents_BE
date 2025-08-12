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

    // A) TẤT CẢ assignments của 1 course (đi đúng chuỗi lesson→chapter→syllabus→course)
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

    // B) Assignments của 1 course nhưng CHỈ khi student đã ENROLLED (lọc enrollment ở DB)
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
            @Param("courseId")  String courseId
    );

    // C) Assignments của TẤT CẢ các course mà student đã ENROLLED
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

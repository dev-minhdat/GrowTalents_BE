package com.growtalents.repository;

import com.growtalents.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, String> {

    // Code từ TuDat - Fixed field name
    @Query("SELECT cs FROM ClassSession cs " +
           "JOIN FETCH cs.course " +
           "WHERE cs.course.courseId IN (" +
           "    SELECT sc.course.courseId FROM StudentCourse sc " +
           "    WHERE sc.student.userId = :studentId " +
           "    AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED" +
           ") " +
           "AND cs.sessionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY cs.sessionDate ASC, cs.startTime ASC")
    List<ClassSession> findStudentClassSessionsBetweenDates(
            @Param("studentId") String studentId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(cs) FROM ClassSession cs " +
           "WHERE cs.course.courseId IN (" +
           "    SELECT sc.course.courseId FROM StudentCourse sc " +
           "    WHERE sc.student.userId = :studentId " +
           "    AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED" +
           ")")
    int countTotalSessionsByStudentId(@Param("studentId") String studentId);

    // Code từ master
    @Query("""
       select count(cs) > 0
       from ClassSession cs
       join cs.course c
       join TeacherCourse tc on tc.course = c
       where tc.teacher.userId = :teacherId
         and cs.sessionDate = :date
         and cs.sessionId <> :excludeSessionId
         and cs.startTime < :proposedEnd
         and cs.endTime   > :proposedStart
    """)
    boolean existsTimeConflict(@Param("teacherId") String teacherId,
                               @Param("date") LocalDate date,
                               @Param("proposedStart") LocalTime proposedStart,
                               @Param("proposedEnd") LocalTime proposedEnd,
                               @Param("excludeSessionId") String excludeSessionId);

    @Query("""
    select cs
    from ClassSession cs
    join cs.course c
    join TeacherCourse tc on tc.course = c
    where tc.teacher.userId = :teacherId
      and cs.rescheduleStatus is not null
    order by cs.rescheduleSubmittedAt desc
    """)
    List<ClassSession> findReschedulesByTeacher(@Param("teacherId") String teacherId);
}

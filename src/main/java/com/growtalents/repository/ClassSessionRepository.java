package com.growtalents.repository;

import com.growtalents.model.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClassSessionRepository extends JpaRepository<ClassSession, String> {
    
    @Query("SELECT cs FROM ClassSession cs " +
           "JOIN FETCH cs.course " +
           "WHERE cs.course.courseId IN (" +
           "    SELECT sc.course.courseId FROM StudentCourse sc " +
           "    WHERE sc.student.userId = :studentId " +
           "    AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED" +
           ") " +
           "AND cs.sessionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY cs.sessionDate ASC, cs.startDateTime ASC")
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
}

package com.growtalents.repository;

import com.growtalents.model.Attendance;
import com.growtalents.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    
    @Query("SELECT COUNT(a) FROM Attendance a " +
           "WHERE a.student.userId = :studentId " +
           "AND a.status = com.growtalents.enums.AttendanceStatus.PRESENT")
    int countPresentAttendanceByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(a) FROM Attendance a " +
           "WHERE a.student.userId = :studentId")
    int countTotalAttendanceByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT a FROM Attendance a " +
           "WHERE a.session.sessionId = :sessionId " +
           "AND a.attendanceDate = :date")
    List<Attendance> findBySessionIdAndDate(@Param("sessionId") String sessionId, @Param("date") java.time.LocalDate date);
    
    @Query("SELECT a FROM Attendance a " +
           "WHERE a.student.userId = :studentId " +
           "ORDER BY a.attendanceDate DESC")
    List<Attendance> findByStudentIdOrderByDateDesc(@Param("studentId") String studentId);
    
    @Query("SELECT a FROM Attendance a " +
           "JOIN a.session s " +
           "JOIN s.course c " +
           "WHERE c.courseId IN (" +
           "    SELECT tc.course.courseId FROM TeacherCourse tc " +
           "    WHERE tc.teacher.userId = :teacherId" +
           ") " +
           "ORDER BY a.attendanceDate DESC")
    List<Attendance> findByTeacherIdOrderByDateDesc(@Param("teacherId") String teacherId);
    
    @Query("SELECT COUNT(a) FROM Attendance a " +
           "WHERE a.student.userId = :studentId " +
           "AND a.status = :status")
    int countAttendanceByStudentIdAndStatus(@Param("studentId") String studentId, @Param("status") AttendanceStatus status);
    
    @Query("SELECT a FROM Attendance a " +
           "WHERE a.session.course.courseId = :courseId " +
           "AND a.attendanceDate = :date")
    List<Attendance> findByCourseIdAndDate(@Param("courseId") String courseId, @Param("date") java.time.LocalDate date);
    
    // Methods cần thiết cho AttendanceServiceImpl
    @Query("SELECT a FROM Attendance a WHERE a.session.sessionId = :sessionId")
    List<Attendance> findBySessionId(@Param("sessionId") String sessionId);
    
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Attendance a " +
           "WHERE a.session.sessionId = :sessionId AND a.student.userId = :studentId")
    boolean existsBySessionIdAndStudentId(@Param("sessionId") String sessionId, @Param("studentId") String studentId);
}

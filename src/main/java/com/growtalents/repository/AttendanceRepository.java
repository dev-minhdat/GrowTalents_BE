package com.growtalents.repository;

import com.growtalents.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    
    @Query("SELECT COUNT(a) FROM Attendance a " +
           "WHERE a.student.userId = :studentId " +
           "AND a.status = com.growtalents.enums.AttendanceStatus.PRESENT")
    int countPresentAttendanceByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(a) FROM Attendance a " +
           "WHERE a.student.userId = :studentId")
    int countTotalAttendanceByStudentId(@Param("studentId") String studentId);
}

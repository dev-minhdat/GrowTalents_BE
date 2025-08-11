package com.growtalents.repository;

import com.growtalents.enums.StudentCourseStatus;
import com.growtalents.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, String> {
    
    @Query("SELECT sc FROM StudentCourse sc " +
           "JOIN FETCH sc.course " +
           "WHERE sc.student.userId = :studentId " +
           "AND sc.status = :status " +
           "ORDER BY sc.registrationDate DESC")
    List<StudentCourse> findByStudentIdAndStatus(@Param("studentId") String studentId, 
                                                 @Param("status") StudentCourseStatus status);
    
    @Query("SELECT sc FROM StudentCourse sc " +
           "JOIN FETCH sc.course " +
           "WHERE sc.student.userId = :studentId " +
           "ORDER BY sc.registrationDate DESC")
    List<StudentCourse> findByStudentId(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(sc) FROM StudentCourse sc " +
           "WHERE sc.student.userId = :studentId " +
           "AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED")
    int countEnrolledCoursesByStudentId(@Param("studentId") String studentId);
}

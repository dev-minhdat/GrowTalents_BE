package com.growtalents.repository;

import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import com.growtalents.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    
    // Methods from master branch
    List<Course> findByNameContainingIgnoreCase(String keyword);
    List<Course> findByStatus(CourseStatus status);
    List<Course> findByType(CourseType type);
    List<Course> findByStatusAndType(CourseStatus status, CourseType type);
    
    // Methods from TuDat branch (for student controller functionality)
    @Query("""
        SELECT c FROM Course c 
        JOIN SemesterCourse sc ON sc.course.courseId = c.courseId 
        WHERE sc.semester.semesterId = :semesterId
        ORDER BY c.name
    """)
    List<Course> findBySemesterId(@Param("semesterId") String semesterId);
    
    @Query("""
        SELECT u.userName FROM Course c 
        JOIN TeacherCourse tc ON tc.course.courseId = c.courseId 
        JOIN tc.teacher u 
        WHERE c.courseId = :courseId
        ORDER BY tc.teacherCourseId 
        LIMIT 1
    """)
    String findTeacherNameByCourseId(@Param("courseId") String courseId);
    
    @Query("""
        SELECT COUNT(sc) FROM StudentCourse sc 
        WHERE sc.course.courseId = :courseId 
        AND sc.status = com.growtalents.enums.StudentCourseStatus.ENROLLED
    """)
    Integer countStudentsByCourseId(@Param("courseId") String courseId);
}

package com.growtalents.repository;

import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.enums.AssignedRole;
import com.growtalents.model.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Integer> {

    @Query("""
       select distinct new com.growtalents.dto.response.Course.CourseResponseDTO(
           c.courseId, c.name, c.tuitionFee, c.duration, c.description, c.type, c.status
       )
       from TeacherCourse tc
       join tc.course c
       where tc.teacher.userId = :teacherId
         and c.status = com.growtalents.enums.CourseStatus.ACTIVE
       """)
    List<CourseResponseDTO> findActiveCoursesByTeacher(@Param("teacherId") String teacherId);

    // Method cần thiết cho AttendanceServiceImpl
    @Query("SELECT tc FROM TeacherCourse tc WHERE tc.teacher.userId = :teacherId")
    List<TeacherCourse> findByTeacherUserId(@Param("teacherId") String teacherId);

}

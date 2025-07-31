package com.growtalents.mapper;

import com.growtalents.dto.request.TeacherCourse.TeacherCourseCreateRequestDTO;
import com.growtalents.dto.response.TeacherCourse.TeacherCourseResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.model.TeacherCourse;
import org.springframework.stereotype.Component;

@Component
public class TeacherCourseMapper {
    public static TeacherCourse toEntity(TeacherCourseCreateRequestDTO dto, int generatedId, AppUser teacher, Course course) {
        TeacherCourse tc = new TeacherCourse();
        tc.setTeacherCourseId(generatedId);
        tc.setAssignedRole(dto.getAssignedRole() != null ? com.growtalents.enums.AssignedRole.valueOf(dto.getAssignedRole()) : null);
        tc.setHourlyRate(dto.getHourlyRate());
        tc.setTeacher(teacher);
        tc.setCourse(course);
        return tc;
    }

    public static TeacherCourseResponseDTO toResponseDTO(TeacherCourse tc) {
        return TeacherCourseResponseDTO.builder()
                .teacherCourseId(tc.getTeacherCourseId())
                .assignedRole(tc.getAssignedRole() != null ? tc.getAssignedRole().name() : null)
                .hourlyRate(tc.getHourlyRate())
                .teacherId(tc.getTeacher() != null ? tc.getTeacher().getUserId() : null)
                .courseId(tc.getCourse() != null ? tc.getCourse().getCourseId() : null)
                .build();
    }
}

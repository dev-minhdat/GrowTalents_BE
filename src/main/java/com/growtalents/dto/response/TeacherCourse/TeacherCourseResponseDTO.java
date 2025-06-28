package com.growtalents.dto.response.TeacherCourse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherCourseResponseDTO {
    private int teacherCourseId;
    private String assignedRole;
    private int hourlyRate;
    private String teacherId;
    private String courseId;
}

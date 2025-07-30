package com.growtalents.dto.request.TeacherCourse;

import lombok.Data;

@Data
public class TeacherCourseCreateRequestDTO {
    private String assignedRole;
    private int hourlyRate;
    private String teacherId;
    private String courseId;
}

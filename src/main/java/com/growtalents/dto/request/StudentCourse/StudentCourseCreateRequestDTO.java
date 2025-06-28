package com.growtalents.dto.request.StudentCourse;

import lombok.Data;

@Data
public class StudentCourseCreateRequestDTO {
    private String studentId;
    private String courseId;
    private String registrationDate;
    private String droppedOutDate;
    private String status;
}

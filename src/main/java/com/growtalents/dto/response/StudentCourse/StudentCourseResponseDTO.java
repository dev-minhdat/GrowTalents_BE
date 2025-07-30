package com.growtalents.dto.response.StudentCourse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentCourseResponseDTO {
    private String studentCourseId;
    private String studentId;
    private String courseId;
    private String registrationDate;
    private String droppedOutDate;
    private String status;
}

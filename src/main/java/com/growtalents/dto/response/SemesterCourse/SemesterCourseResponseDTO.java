package com.growtalents.dto.response.SemesterCourse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SemesterCourseResponseDTO {
    private String semesterCourseId;
    private String semesterId;
    private String courseId;
}

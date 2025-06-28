package com.growtalents.dto.request.SemesterCourse;

import lombok.Data;

@Data
public class SemesterCourseCreateRequestDTO {
    private String semesterId;
    private String courseId;
}

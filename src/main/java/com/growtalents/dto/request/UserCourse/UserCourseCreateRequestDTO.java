package com.growtalents.dto.request.UserCourse;

import lombok.Data;

@Data
public class UserCourseCreateRequestDTO {
    private String userId;
    private String courseId;
    private String role;
}

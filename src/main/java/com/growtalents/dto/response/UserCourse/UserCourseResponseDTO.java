package com.growtalents.dto.response.UserCourse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCourseResponseDTO {
    private String id;
    private String userId;
    private String courseId;
    private String role;
}

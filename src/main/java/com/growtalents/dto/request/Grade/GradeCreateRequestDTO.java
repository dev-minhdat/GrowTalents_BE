package com.growtalents.dto.request.Grade;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GradeCreateRequestDTO {
    private String studentId;
    private String courseId;
    private String assignmentId;
    private String classSessionId;
    private Float score;
    private String comment;
}

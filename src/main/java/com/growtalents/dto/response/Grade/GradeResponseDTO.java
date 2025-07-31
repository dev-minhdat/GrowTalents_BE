package com.growtalents.dto.response.Grade;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GradeResponseDTO {
    private String gradeId;
    private String courseId;
    private String assignmentId;
    private String classSessionId;
    private String courseName; // field name in Course
    private String assignmentName; // field title in Assignment
    private String classSessionName; // field topic in ClassSession
    private Float score;
    private String comment;
}

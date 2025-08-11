package com.growtalents.dto.response.Grade;

import com.growtalents.enums.PerformanceLevel;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GradeResponseDTO {
    private String gradeId;
//    private String courseId;
    private String assignmentId;
//    private String classSessionId;
//    private String courseName;
    private String assignmentName; // field title in Assignment
//    private String classSessionName;
    private Float score;
    private String comment;
    private PerformanceLevel performanceLevel;
}

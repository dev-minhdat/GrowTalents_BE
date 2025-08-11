package com.growtalents.dto.response.Student;

import com.growtalents.enums.AssignmentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAssignmentResponseDTO {
    private String assignmentId;
    private String title;
    private String description;
    private AssignmentType assignmentType;
    private String uploadFileUrl;
    private String courseId;
    private String courseName;
    private LocalDateTime createdAt;
    private boolean hasSubmitted;
    private LocalDateTime submittedAt;
    private String submissionFileUrl;
    private Float score;
    private String teacherComment;
}

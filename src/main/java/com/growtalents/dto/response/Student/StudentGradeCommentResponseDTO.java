package com.growtalents.dto.response.Student;

import com.growtalents.enums.AssignmentType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentGradeCommentResponseDTO {
    private String assignmentId;
    private String assignmentTitle;
    private AssignmentType assignmentType;
    private String courseId;
    private String courseName;
    private Float score;
    private String teacherComment;
    private LocalDateTime submittedAt;
    private LocalDateTime gradedAt;
    private String teacherName;
}

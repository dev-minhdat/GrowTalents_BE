package com.growtalents.dto.response.Assignment;

import com.growtalents.enums.AssignmentType;
import com.growtalents.model.Course;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponseDTO {
    private String assignmentId;
    private String courseId;
    private String courseName;
    private String title;
    private String description;
    private String uploadFileUrl;
    private AssignmentType assignmentType;
}

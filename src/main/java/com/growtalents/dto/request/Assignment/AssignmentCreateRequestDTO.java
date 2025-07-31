package com.growtalents.dto.request.Assignment;

import com.growtalents.enums.AssignmentType;
import com.growtalents.model.Course;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentCreateRequestDTO {
    private String title;
    private String description;
    private String uploadFileUrl;
    private AssignmentType assignmentType;
    private String courseId;
}

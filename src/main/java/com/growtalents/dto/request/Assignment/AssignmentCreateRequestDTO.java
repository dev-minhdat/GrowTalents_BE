package com.growtalents.dto.request.Assignment;

import com.growtalents.enums.AssignmentType;
import com.growtalents.model.Course;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentCreateRequestDTO {
    private String title;
    private String description;
    private String uploadFileUrl;
    private LocalDate deadline;
    private AssignmentType assignmentType;
    private String lessonId;
}

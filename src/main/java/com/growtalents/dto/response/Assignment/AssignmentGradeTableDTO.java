package com.growtalents.dto.response.Assignment;

import com.growtalents.enums.PerformanceLevel;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentGradeTableDTO {
    private String studentId;
    private String studentName;
    private Float score;                     // nullable
    private PerformanceLevel performanceLevel;  // nullable
    private String comment;                 // nullable
}
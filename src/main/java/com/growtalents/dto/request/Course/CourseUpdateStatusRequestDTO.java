package com.growtalents.dto.request.Course;

import com.growtalents.enums.CourseStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseUpdateStatusRequestDTO {
    private CourseStatus status; // ACTIVE | COMPLETED | INACTIVE
}

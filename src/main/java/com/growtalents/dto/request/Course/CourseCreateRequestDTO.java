package com.growtalents.dto.request.Course;

import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseCreateRequestDTO {
    private String nameCourse;
    private Integer tuitionFee;
    private String duration;
    private String description;
    private CourseType type;
//    private String syllabus;
    private CourseStatus status;
}

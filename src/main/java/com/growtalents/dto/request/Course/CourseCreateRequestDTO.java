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
    private String imageUrl;
    private String nameCourse;
    private CourseType courseType;
    private Integer tuitionFee;
    private Integer duration;
    private String description;
    private String createdByAdminId;
}

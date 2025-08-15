package com.growtalents.dto.response.Course;

import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseResponseDTO {
    private String courseId;
    private String nameCourse;
    private Integer tuitionFee;
    private Integer duration;
    private String description;
    private CourseType type;
    private CourseStatus status;
    private String imageUrl;
    private String createdBy;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}

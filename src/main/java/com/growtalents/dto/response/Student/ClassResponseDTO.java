package com.growtalents.dto.response.Student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassResponseDTO {
    private String courseId;
    private String courseName;
    private String courseType;
    private String courseStatus;
    private Integer duration; // Changed from Integer to String
    private Integer tuitionFee; // Changed from Double to Integer
    private String description;
    private String teacherName;
    private Integer totalStudents;
}

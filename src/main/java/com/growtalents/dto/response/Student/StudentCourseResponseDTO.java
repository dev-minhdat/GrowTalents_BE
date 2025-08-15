package com.growtalents.dto.response.Student;

import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import com.growtalents.enums.StudentCourseStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseResponseDTO {
    private String courseId;
    private String courseName;
    private String description;
    private CourseType courseType;
    private CourseStatus courseStatus;
    private Integer tuitionFee;
    private Integer duration;
    private StudentCourseStatus enrollmentStatus;
    private LocalDate registrationDate;
    private LocalDate droppedOutDate;
}

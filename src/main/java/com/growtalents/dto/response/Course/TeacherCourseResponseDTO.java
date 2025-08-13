package com.growtalents.dto.response.Course;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeacherCourseResponseDTO {
    private String id;
    private String courseName;
    private String courseCode;
    private String description;
    private Integer totalStudents;
    private String startDate;
    private String endDate;
    private String status;
    private List<StudentInfoDTO> students;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class StudentInfoDTO {
        private String id;
        private String name;
        private String email;
        private String phone;
        private String avatar;
        private String enrollmentDate;
    }
}
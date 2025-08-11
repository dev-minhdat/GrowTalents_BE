package com.growtalents.dto.response.Student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentStatisticsResponseDTO {
    private Float averageScore;
    private int completedAssignments;
    private int pendingAssignments;
    private int totalAssignments;
    private int totalCoursesEnrolled;
    private int totalClassSessionsAttended;
    private Float attendanceRate;
}

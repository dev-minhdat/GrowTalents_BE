package com.growtalents.dto.response.Attendance;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceHistoryResponseDTO {
    private String id;
    private String date;
    private String courseId;
    private String courseName;
    private String sessionId;
    private String teacherId;
    private String teacherName;
    private Integer totalStudents;
    private Integer presentCount;
    private Integer absentCount;
    private Integer lateCount;
    private Integer permittedAbsenceCount;
    private Double attendanceRate;
    private String createdAt;
    private String updatedAt;
}
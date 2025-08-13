package com.growtalents.dto.response.Attendance;

import com.growtalents.enums.AttendanceStatus;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceSessionResponseDTO {
    private String sessionId;
    private String courseId;
    private String courseName;
    private String date;
    private String teacherId;
    private String teacherName;
    private Integer totalStudents;
    private Integer presentCount;
    private Integer absentCount;
    private Integer lateCount;
    private Integer permittedAbsenceCount;
    private Double attendanceRate;
    private List<AttendanceRecordDTO> attendanceRecords;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class AttendanceRecordDTO {
        private String attendanceId;
        private String studentId;
        private String studentName;
        private String studentAvatar;
        private AttendanceStatus status;
        private String note;
    }
}
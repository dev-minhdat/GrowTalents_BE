package com.growtalents.dto.response.Attendance;

import com.growtalents.enums.AttendanceStatus;
import com.growtalents.model.ClassSession;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AttendanceResponseDTO {
    private String attendanceId;
    private AttendanceStatus status;
    private ClassSession session;
    private String studentId;
    private String studentName;
}

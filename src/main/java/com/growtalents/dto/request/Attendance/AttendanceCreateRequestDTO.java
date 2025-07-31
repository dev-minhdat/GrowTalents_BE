package com.growtalents.dto.request.Attendance;

import com.growtalents.enums.AttendanceStatus;
import com.growtalents.model.AppUser;
import com.growtalents.model.ClassSession;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AttendanceCreateRequestDTO {
    private AttendanceStatus status;
    private String sessionId;
    private String studentId;
}

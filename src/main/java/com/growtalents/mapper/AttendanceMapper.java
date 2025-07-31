package com.growtalents.mapper;

import com.growtalents.dto.request.Attendance.AttendanceCreateRequestDTO;
import com.growtalents.dto.response.Attendance.AttendanceResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Attendance;
import com.growtalents.model.ClassSession;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {
    // Phần session và user nho xu ly tren service
    public Attendance toEntity(AttendanceCreateRequestDTO dto, String generateId, ClassSession session, AppUser user) {
        return Attendance.builder()
                .attendanceId(generateId)
                .status(dto.getStatus())
                .session(session)
                .student(user)
                .build();
    }
    public AttendanceResponseDTO toResponseDTO(Attendance attendance) {
        return AttendanceResponseDTO.builder()
                .attendanceId(attendance.getAttendanceId())
                .status(attendance.getStatus())
                .session(attendance.getSession())
                .build();
    }
}

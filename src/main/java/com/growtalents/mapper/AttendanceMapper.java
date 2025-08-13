package com.growtalents.mapper;

import com.growtalents.dto.request.Attendance.AttendanceCreateRequestDTO;
import com.growtalents.dto.response.Attendance.AttendanceResponseDTO;
import com.growtalents.dto.response.Attendance.AttendanceSessionResponseDTO;
import com.growtalents.dto.response.Attendance.AttendanceHistoryResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Attendance;
import com.growtalents.model.ClassSession;
import com.growtalents.enums.AttendanceStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttendanceMapper {
    
    public Attendance toEntity(AttendanceCreateRequestDTO.AttendanceRecordRequestDTO dto, 
                              String generateId, ClassSession session, AppUser student, LocalDate date) {
        return Attendance.builder()
                .attendanceId(generateId)
                .status(dto.getStatus())
                .note(dto.getNote())
                .attendanceDate(date)
                .session(session)
                .student(student)
                .build();
    }
    
    public AttendanceResponseDTO toResponseDTO(Attendance attendance) {
        return AttendanceResponseDTO.builder()
                .attendanceId(attendance.getAttendanceId())
                .status(attendance.getStatus())
                .session(attendance.getSession())
                .studentId(attendance.getStudent().getUserId())
                .studentName(attendance.getStudent().getUserName())
                .build();
    }
    
    public AttendanceSessionResponseDTO.AttendanceRecordDTO toAttendanceRecordDTO(Attendance attendance) {
        return AttendanceSessionResponseDTO.AttendanceRecordDTO.builder()
                .attendanceId(attendance.getAttendanceId())
                .studentId(attendance.getStudent().getUserId())
                .studentName(attendance.getStudent().getUserName())
                .status(attendance.getStatus())
                .note(attendance.getNote())
                .build();
    }
    
    public AttendanceSessionResponseDTO toSessionResponseDTO(List<Attendance> attendances, 
                                                           ClassSession session, String date) {
        if (attendances.isEmpty()) {
            return AttendanceSessionResponseDTO.builder()
                    .sessionId(session.getSessionId())
                    .courseId(session.getCourse().getCourseId())
                    .courseName(session.getCourse().getName())
                    .date(date)
                    .totalStudents(0)
                    .presentCount(0)
                    .absentCount(0)
                    .lateCount(0)
                    .permittedAbsenceCount(0)
                    .attendanceRate(0.0)
                    .attendanceRecords(List.of())
                    .build();
        }
        
        // Calculate statistics
        int presentCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();
        int absentCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                .count();
        int lateCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.LATE)
                .count();
        int permittedAbsenceCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PERMITTED_ABSENCE)
                .count();
        
        int totalStudents = attendances.size();
        double attendanceRate = totalStudents > 0 ? 
                ((double) (presentCount + lateCount) / totalStudents) * 100 : 0.0;
        
        List<AttendanceSessionResponseDTO.AttendanceRecordDTO> records = attendances.stream()
                .map(this::toAttendanceRecordDTO)
                .collect(Collectors.toList());
        
        return AttendanceSessionResponseDTO.builder()
                .sessionId(session.getSessionId())
                .courseId(session.getCourse().getCourseId())
                .courseName(session.getCourse().getName())
                .date(date)
                .totalStudents(totalStudents)
                .presentCount(presentCount)
                .absentCount(absentCount)
                .lateCount(lateCount)
                .permittedAbsenceCount(permittedAbsenceCount)
                .attendanceRate(Math.round(attendanceRate * 100.0) / 100.0)
                .attendanceRecords(records)
                .build();
    }
    
    public AttendanceHistoryResponseDTO toHistoryResponseDTO(String date, String courseId, 
                                                           String courseName, List<Attendance> attendances) {
        int presentCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();
        int absentCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                .count();
        int lateCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.LATE)
                .count();
        int permittedAbsenceCount = (int) attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PERMITTED_ABSENCE)
                .count();
        
        int totalStudents = attendances.size();
        double attendanceRate = totalStudents > 0 ? 
                ((double) (presentCount + lateCount) / totalStudents) * 100 : 0.0;
        
        return AttendanceHistoryResponseDTO.builder()
                .date(date)
                .courseId(courseId)
                .courseName(courseName)
                .totalStudents(totalStudents)
                .presentCount(presentCount)
                .absentCount(absentCount)
                .lateCount(lateCount)
                .permittedAbsenceCount(permittedAbsenceCount)
                .attendanceRate(Math.round(attendanceRate * 100.0) / 100.0)
                .build();
    }
}

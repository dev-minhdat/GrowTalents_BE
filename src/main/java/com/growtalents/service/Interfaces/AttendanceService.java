package com.growtalents.service.Interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AttendanceService {
    
    // Level A: Get danh sách lớp theo ID giáo viên
    List<Map<String, Object>> getTeacherClasses(String teacherId);
    
    // Level B: Create điểm danh mới
    String createAttendance(Map<String, Object> request);
    
    // Level B: Update điểm danh
    String updateAttendance(String attendanceId, Map<String, Object> request);
    
    // Level C: Report lịch sử điểm danh
    List<Map<String, Object>> getAttendanceHistory(String teacherId, LocalDate startDate, LocalDate endDate);
    
    // Bonus APIs
    List<Map<String, Object>> getAttendanceBySession(String sessionId);
    List<Map<String, Object>> getAttendanceByCourse(String courseId, LocalDate date);
}
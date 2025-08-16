package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.ClassSession.ClassSessionCreateRequestDTO;
import com.growtalents.dto.request.ClassSession.ClassSessionRescheduleRequestDTO;
import com.growtalents.dto.response.ClassSession.ClassSessionResponseDTO;

import java.util.List;

public interface ClassSessionService {
    void requestReschedule(String teacherId, ClassSessionRescheduleRequestDTO dto);
    void approveReschedule(String sessionId);         // Admin dùng
    void rejectReschedule(String sessionId, String reason); // Admin dùng
    List<ClassSessionResponseDTO> getAllRescheduled(String teacherId);
    
    /**
     * Tạo buổi học mới cho giáo viên
     * @param teacherId ID của giáo viên
     * @param dto Thông tin buổi học
     */
    void createClassSession(String teacherId, ClassSessionCreateRequestDTO dto);
    
    /**
     * Lấy danh sách buổi học của giáo viên
     * @param teacherId ID của giáo viên
     * @return Danh sách buổi học
     */
    List<ClassSessionResponseDTO> getTeacherClassSessions(String teacherId);
}

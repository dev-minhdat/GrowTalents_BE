package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.ClassSession.ClassSessionRescheduleRequestDTO;
import com.growtalents.dto.response.ClassSession.ClassSessionResponseDTO;

import java.util.List;

public interface ClassSessionService {
    void requestReschedule(String teacherId, ClassSessionRescheduleRequestDTO dto);
    void approveReschedule(String sessionId);         // Admin dùng
    void rejectReschedule(String sessionId, String reason); // Admin dùng
    List<ClassSessionResponseDTO> getAllRescheduled(String teacherId);
}

package com.growtalents.mapper;

import com.growtalents.dto.request.TeachingLog.TeachingLogCreateRequestDTO;
import com.growtalents.dto.response.TeachingLog.TeachingLogResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.ClassSession;
import com.growtalents.model.TeachingLog;

public class TeachingLogMapper {
    public static TeachingLog toEntity(TeachingLogCreateRequestDTO dto, String generatedId, AppUser teacher, ClassSession session) {
        TeachingLog log = new TeachingLog();
        log.setLogId(generatedId);
        log.setTeacher(teacher);
        log.setSession(session);
        log.setTeachingHours(dto.getTeachingHours());
        log.setSalaryAmount(dto.getSalaryAmount());
        log.setRequestChange(dto.getRequestChange());
        log.setStatus(dto.getStatus() != null ? com.growtalents.enums.TeachingLogStatus.valueOf(dto.getStatus()) : null);
        return log;
    }

    public static TeachingLogResponseDTO toResponseDTO(TeachingLog log) {
        return TeachingLogResponseDTO.builder()
                .logId(log.getLogId())
                .teacherId(log.getTeacher() != null ? log.getTeacher().getUserId() : null)
                .sessionId(log.getSession() != null ? log.getSession().getSessionId() : null)
                .teachingHours(log.getTeachingHours())
                .salaryAmount(log.getSalaryAmount())
                .requestChange(log.getRequestChange())
                .status(log.getStatus() != null ? log.getStatus().name() : null)
                .build();
    }
}

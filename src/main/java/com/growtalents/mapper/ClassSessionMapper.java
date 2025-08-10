package com.growtalents.mapper;

import com.growtalents.dto.request.ClassSession.ClassSessionCreateRequestDTO;
import com.growtalents.dto.response.ClassSession.ClassSessionResponseDTO;
import com.growtalents.model.ClassSession;
import com.growtalents.model.Course;
import org.springframework.stereotype.Component;

@Component
public class ClassSessionMapper {
    public ClassSession toEntity(ClassSessionCreateRequestDTO dto, String generateId, Course course) {
        return ClassSession.builder()
                .sessionId(generateId)
                .topic(dto.getTopic())
                .sessionDate(dto.getSessionDate())
                .durationInMinutes(dto.getDurationInMinutes())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .course(course)
                .build();
    }
    public ClassSessionResponseDTO toResponseDTO(ClassSession dto) {
        return ClassSessionResponseDTO.builder()
                .sessionId(dto.getSessionId())
                .topic(dto.getTopic())
                .sessionDate(dto.getSessionDate())
                .durationInMinutes(dto.getDurationInMinutes())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .course(dto.getCourse())
                .build();
    }
}

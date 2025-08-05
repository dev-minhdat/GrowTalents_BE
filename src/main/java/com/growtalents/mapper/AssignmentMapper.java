package com.growtalents.mapper;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.model.Assignment;
import com.growtalents.model.Course;
import com.growtalents.model.Lesson;
import org.springframework.stereotype.Component;

// Xai component để DI ở Service
@Component
public class AssignmentMapper {
    public Assignment toEntity (AssignmentCreateRequestDTO dto, String generateId, Lesson lesson) {
        return Assignment.builder()
                .assignmentId(generateId)
                .assignmentType(dto.getAssignmentType())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .uploadFileUrl(dto.getUploadFileUrl())
                .lesson(lesson)
                .build();
    }
    public AssignmentResponseDTO toResponseDTO (Assignment entity) {
        return AssignmentResponseDTO.builder()
                .assignmentId(entity.getAssignmentId())
                .assignmentType(entity.getAssignmentType())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .uploadFileUrl(entity.getUploadFileUrl())
                .lessonId(entity.getLesson().getLessonId())
                .lessonTitle(entity.getLesson().getTitle())
                .build();
    }
}

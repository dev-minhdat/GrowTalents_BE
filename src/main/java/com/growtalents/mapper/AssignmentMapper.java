package com.growtalents.mapper;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.dto.response.Assignment.AssignmentStudentStatusResponseDTO;
import com.growtalents.dto.response.Assignment.AssignmentTableResponseDTO;
import com.growtalents.model.Assignment;
import com.growtalents.model.Course;
import com.growtalents.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AssignmentResponseDTO> toResponseDTO(List<Assignment> assignments) {
        return assignments.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
    public static AssignmentStudentStatusResponseDTO toStudentStatusDTO (Assignment a, boolean submitted) {
        return AssignmentStudentStatusResponseDTO.builder()
                .assignmentId(a.getAssignmentId())
                .title(a.getTitle())
                .submitted(submitted)
                .deadline(a.getDeadline())
                .createdAt(a.getCreatedAt())
                .build();
    }
    public AssignmentTableResponseDTO toTableDTO(Assignment entity, Long submittedCount) {
        return AssignmentTableResponseDTO.builder()
                .assignmentId(entity.getAssignmentId())
                .title(entity.getTitle())
                .deadline(entity.getDeadline())
                .studentSubmittedCount(submittedCount)
                .build();
    }
}

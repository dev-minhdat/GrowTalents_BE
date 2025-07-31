package com.growtalents.mapper;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.model.Assignment;
import com.growtalents.model.Course;
import org.springframework.stereotype.Component;

// Xai component để DI ở Service
@Component
public class AssignmentMapper {
    // Phan CourseId (Course) nhớ xử lý trên service
    public Assignment toEntity (AssignmentCreateRequestDTO dto, String generateId, Course course) {
        return Assignment.builder()
                .assignmentId(generateId)
                .assignmentType(dto.getAssignmentType())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .uploadFileUrl(dto.getUploadFileUrl())
                .course(course)
                .build();
    }
    public AssignmentResponseDTO toResponseDTO (Assignment entity) {
        return AssignmentResponseDTO.builder()
                .assignmentId(entity.getAssignmentId())
                .assignmentType(entity.getAssignmentType())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .uploadFileUrl(entity.getUploadFileUrl())
                .courseId(entity.getCourse().getCourseId())
                .courseName(entity.getCourse().getName())
                .build();
    }
}

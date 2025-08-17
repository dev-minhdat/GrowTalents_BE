package com.growtalents.mapper;

import com.growtalents.dto.request.Grade.GradeCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentTableResponseDTO;
import com.growtalents.dto.response.Grade.GradeResponseDTO;
import com.growtalents.model.*;

public class GradeMapper {
    public Grade toEntity (GradeCreateRequestDTO dto, String generateId, AppUser student, Assignment assignment) {
        return Grade.builder()
                .gradeId(generateId)
                .student(student)
                .assignment(assignment)
                .score(dto.getScore())
                .comment(dto.getComment())
                .build();
    }
    public GradeResponseDTO toResponseDTO (Grade grade) {
        return GradeResponseDTO.builder()
                .gradeId(grade.getGradeId())
                .assignmentId(grade.getAssignment().getAssignmentId())
                .assignmentName(grade.getAssignment().getTitle())
                .score(grade.getScore())
                .comment(grade.getComment())
                .performanceLevel(grade.getPerformanceLevel())
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

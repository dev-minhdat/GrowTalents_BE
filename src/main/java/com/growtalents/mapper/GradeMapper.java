package com.growtalents.mapper;

import com.growtalents.dto.request.Grade.GradeCreateRequestDTO;
import com.growtalents.dto.response.Grade.GradeResponseDTO;
import com.growtalents.model.*;

public class GradeMapper {
    public Grade toEntity (GradeCreateRequestDTO dto, String generateId, AppUser student, Assignment assignment, Course course, ClassSession classSession ) {
        return Grade.builder()
                .gradeId(generateId)
                .student(student)
                .assignment(assignment)
                .course(course)
                .classSession(classSession)
                .score(dto.getScore())
                .comment(dto.getComment())
                .build();
    }
    public GradeResponseDTO toResponseDTO (Grade grade) {
        return GradeResponseDTO.builder()
                .gradeId(grade.getGradeId())
                .courseId(grade.getCourse().getCourseId())
                .assignmentId(grade.getAssignment().getAssignmentId())
                .classSessionId(grade.getClassSession().getSessionId())
                .courseName(grade.getCourse().getName())
                .assignmentName(grade.getAssignment().getTitle())
                .classSessionName(grade.getClassSession().getTopic())
                .score(grade.getScore())
                .comment(grade.getComment())
                .build();
    }
}

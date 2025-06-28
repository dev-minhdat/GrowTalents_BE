package com.growtalents.mapper;

import com.growtalents.dto.request.StudentSubmission.StudentSubmissionCreateRequestDTO;
import com.growtalents.dto.response.StudentSubmission.StudentSubmissionResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Assignment;
import com.growtalents.model.StudentSubmission;

public class StudentSubmissionMapper {
    public static StudentSubmission toEntity(StudentSubmissionCreateRequestDTO dto, String generatedId, Assignment assignment, AppUser student) {
        StudentSubmission ss = new StudentSubmission();
        ss.setSubmissionId(generatedId);
        ss.setAssignment(assignment);
        ss.setStudent(student);
        ss.setFileUrl(dto.getFileUrl());
        ss.setSubmittedAt(dto.getSubmittedAt() != null ? java.time.LocalDateTime.parse(dto.getSubmittedAt()) : null);
        return ss;
    }

    public static StudentSubmissionResponseDTO toResponseDTO(StudentSubmission ss) {
        return StudentSubmissionResponseDTO.builder()
                .submissionId(ss.getSubmissionId())
                .assignmentId(ss.getAssignment() != null ? ss.getAssignment().getAssignmentId() : null)
                .studentId(ss.getStudent() != null ? ss.getStudent().getUserId() : null)
                .fileUrl(ss.getFileUrl())
                .submittedAt(ss.getSubmittedAt() != null ? ss.getSubmittedAt().toString() : null)
                .build();
    }
}

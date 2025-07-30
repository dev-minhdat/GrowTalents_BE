package com.growtalents.dto.request.StudentSubmission;

import lombok.Data;

@Data
public class StudentSubmissionCreateRequestDTO {
    private String assignmentId;
    private String studentId;
    private String fileUrl;
    private String submittedAt;
}

package com.growtalents.dto.response.StudentSubmission;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentSubmissionResponseDTO {
    private String submissionId;
    private String assignmentId;
    private String studentId;
    private String fileUrl;
    private String submittedAt;
}

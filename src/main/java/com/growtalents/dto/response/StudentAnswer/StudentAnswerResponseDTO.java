package com.growtalents.dto.response.StudentAnswer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentAnswerResponseDTO {
    private String id;
    private String studentId;
    private String questionId;
    private String selectedOption;
}

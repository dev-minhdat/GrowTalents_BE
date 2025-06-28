package com.growtalents.dto.request.StudentAnswer;

import lombok.Data;

@Data
public class StudentAnswerCreateRequestDTO {
    private String studentId;
    private String questionId;
    private String selectedOption;
}

package com.growtalents.dto.response.MultipleChoiceQuestion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultipleChoiceQuestionResponseDTO {
    private String questionId;
    private String assignmentId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
}

package com.growtalents.dto.request.MultipleChoiceQuestion;

import lombok.Data;

@Data
public class MultipleChoiceQuestionCreateRequestDTO {
    private String assignmentId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
}

package com.growtalents.mapper;

import com.growtalents.dto.request.MultipleChoiceQuestion.MultipleChoiceQuestionCreateRequestDTO;
import com.growtalents.dto.response.MultipleChoiceQuestion.MultipleChoiceQuestionResponseDTO;
import com.growtalents.model.Assignment;
import com.growtalents.model.MultipleChoiceQuestion;
import org.springframework.stereotype.Component;

@Component
public class MultipleChoiceQuestionMapper {
    public static MultipleChoiceQuestion toEntity(MultipleChoiceQuestionCreateRequestDTO dto, String generatedId, Assignment assignment) {
        return MultipleChoiceQuestion.builder()
                .questionId(generatedId)
                .assignment(assignment)
                .questionText(dto.getQuestionText())
                .optionA(dto.getOptionA())
                .optionB(dto.getOptionB())
                .optionC(dto.getOptionC())
                .optionD(dto.getOptionD())
                .correctOption(dto.getCorrectOption())
                .build();
    }

    public static MultipleChoiceQuestionResponseDTO toResponseDTO(MultipleChoiceQuestion question) {
        return MultipleChoiceQuestionResponseDTO.builder()
                .questionId(question.getQuestionId())
                .assignmentId(question.getAssignment() != null ? question.getAssignment().getAssignmentId() : null)
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .correctOption(question.getCorrectOption())
                .build();
    }
}

package com.growtalents.mapper;

import com.growtalents.dto.request.StudentAnswer.StudentAnswerCreateRequestDTO;
import com.growtalents.dto.response.StudentAnswer.StudentAnswerResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.MultipleChoiceQuestion;
import com.growtalents.model.StudentAnswer;
import org.springframework.stereotype.Component;

@Component
public class StudentAnswerMapper {
    public static StudentAnswer toEntity(StudentAnswerCreateRequestDTO dto, String generatedId, AppUser student, MultipleChoiceQuestion question) {
        StudentAnswer sa = new StudentAnswer();
        sa.setId(generatedId);
        sa.setStudent(student);
        sa.setQuestion(question);
        sa.setSelectedOption(dto.getSelectedOption());
        return sa;
    }

    public static StudentAnswerResponseDTO toResponseDTO(StudentAnswer sa) {
        return StudentAnswerResponseDTO.builder()
                .id(sa.getId())
                .studentId(sa.getStudent() != null ? sa.getStudent().getUserId() : null)
                .questionId(sa.getQuestion() != null ? sa.getQuestion().getQuestionId() : null)
                .selectedOption(sa.getSelectedOption())
                .build();
    }
}

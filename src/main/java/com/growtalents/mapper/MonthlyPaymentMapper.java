package com.growtalents.mapper;

import com.growtalents.dto.request.MonthlyPayment.MonthlyPaymentCreateRequestDTO;
import com.growtalents.dto.response.MonthlyPayment.MonthlyPaymentResponseDTO;
import com.growtalents.model.MonthlyPayment;
import com.growtalents.model.StudentCourse;
import org.springframework.stereotype.Component;

@Component
public class MonthlyPaymentMapper {
    public MonthlyPayment toEntity(MonthlyPaymentCreateRequestDTO dto, String generateId, StudentCourse studentCourse) {
        return MonthlyPayment.builder()
                .monthlyPaymentId(generateId)
                .amount(dto.getAmount())
                .dueDate(dto.getDueDate())
                .paidDate(dto.getPaidDate())
                .studentCourse(studentCourse)
                .build();
    }
    public MonthlyPaymentResponseDTO toResponseDTO(MonthlyPayment entity) {
        return MonthlyPaymentResponseDTO.builder()
                .monthlyPaymentId(entity.getMonthlyPaymentId())
                .amount(entity.getAmount())
                .dueDate(entity.getDueDate())
                .paidDate(entity.getPaidDate())
                .studentCourse(entity.getStudentCourse())
                .build();
    }
}

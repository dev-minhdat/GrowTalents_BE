package com.growtalents.dto.response.MonthlyPayment;

import com.growtalents.model.StudentCourse;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MonthlyPaymentResponseDTO {
    private String monthlyPaymentId;
    private int amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private StudentCourse studentCourse;
}

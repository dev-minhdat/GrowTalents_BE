package com.growtalents.dto.request.MonthlyPayment;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MonthlyPaymentCreateRequestDTO {
    private int amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String studentCourseId;
}

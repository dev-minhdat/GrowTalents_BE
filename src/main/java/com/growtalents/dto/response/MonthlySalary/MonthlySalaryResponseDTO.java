package com.growtalents.dto.response.MonthlySalary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MonthlySalaryResponseDTO {
    private String salaryId;
    private String teacherId;
    private String month;
    private int totalHours;
    private int totalAmount;
    private String paymentDate;
    private String status;
}

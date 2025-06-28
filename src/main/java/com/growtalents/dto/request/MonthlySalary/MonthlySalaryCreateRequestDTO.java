package com.growtalents.dto.request.MonthlySalary;

import lombok.Data;

@Data
public class MonthlySalaryCreateRequestDTO {
    private String teacherId;
    private String month;
    private int totalHours;
    private int totalAmount;
    private String paymentDate; // ISO format
    private String status;
}

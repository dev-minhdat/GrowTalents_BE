package com.growtalents.dto.request.TuitionPayment;

import lombok.Data;

@Data
public class TuitionPaymentCreateRequestDTO {
    private String studentId;
    private String courseId;
    private Integer amountPaid;
    private String paymentDate;
}

package com.growtalents.dto.response.TuitionPayment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TuitionPaymentResponseDTO {
    private String paymentId;
    private String studentId;
    private String courseId;
    private Integer amountPaid;
    private String paymentDate;
}

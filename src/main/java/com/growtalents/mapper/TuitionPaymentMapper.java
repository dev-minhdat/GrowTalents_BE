package com.growtalents.mapper;

import com.growtalents.dto.request.TuitionPayment.TuitionPaymentCreateRequestDTO;
import com.growtalents.dto.response.TuitionPayment.TuitionPaymentResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.model.TuitionPayment;

public class TuitionPaymentMapper {
    public static TuitionPayment toEntity(TuitionPaymentCreateRequestDTO dto, String generatedId, AppUser student, Course course) {
        TuitionPayment tp = new TuitionPayment();
        tp.setPaymentId(generatedId);
        tp.setStudent(student);
        tp.setCourse(course);
        tp.setAmountPaid(dto.getAmountPaid());
        tp.setPaymentDate(dto.getPaymentDate() != null ? java.time.LocalDate.parse(dto.getPaymentDate()) : null);
        return tp;
    }

    public static TuitionPaymentResponseDTO toResponseDTO(TuitionPayment tp) {
        return TuitionPaymentResponseDTO.builder()
                .paymentId(tp.getPaymentId())
                .studentId(tp.getStudent() != null ? tp.getStudent().getUserId() : null)
                .courseId(tp.getCourse() != null ? tp.getCourse().getCourseId() : null)
                .amountPaid(tp.getAmountPaid())
                .paymentDate(tp.getPaymentDate() != null ? tp.getPaymentDate().toString() : null)
                .build();
    }
}

package com.growtalents.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table (name = "MonthlyPayment")
public class MonthlyPayment {
    @Id
    @Column (name = "monthlypayment_id")
    private String monthlyPaymentId;

    @Column (name = "amount")
    private int amount;

    @Column (name = "due_date")
    private LocalDate dueDate;

    @Column (name = "paid_date")
    private LocalDate paidDate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "student_course_id", nullable = false)
    private StudentCourse studentCourse;
}

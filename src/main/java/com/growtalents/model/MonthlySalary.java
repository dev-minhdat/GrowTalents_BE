package com.growtalents.model;

import com.growtalents.enums.SalaryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "MonthlySalary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalary {

    @Id
    private String salaryId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private AppUser teacher;

    @Column(nullable = false)
    private String month; // e.g., "2025-06"

    private int totalHours;

    @Column(nullable = false)
    private int totalAmount;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SalaryStatus status;


}


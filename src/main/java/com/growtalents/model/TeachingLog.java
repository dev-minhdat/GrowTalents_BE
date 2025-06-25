package com.growtalents.model;

import com.growtalents.enums.TeachingLogStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "TeachingLog")
public class TeachingLog {

    @Id
    @Column(name = "log_id")
    private String logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private AppUser teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ClassSession session;

    @Column(name = "teaching_hours")
    private double teachingHours;


    @Column(name = "salary_amount")
    private Integer salaryAmount;

    @Lob
    @Column(name = "request_change")
    private String requestChange;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TeachingLogStatus status;
}

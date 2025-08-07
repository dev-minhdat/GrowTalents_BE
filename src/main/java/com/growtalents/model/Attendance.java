package com.growtalents.model;

import com.growtalents.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table (name = "Attendance")
public class Attendance {
    @Id
    @Column (name = "attendance_id")
    private String attendanceId;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private AttendanceStatus status;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "session_id", nullable = false)
    private ClassSession session;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "student_id",nullable = false)
    private AppUser student;

}

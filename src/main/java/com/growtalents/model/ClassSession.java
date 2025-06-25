package com.growtalents.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table (name = "ClassSession")
public class ClassSession {
    @Id
    @Column(name = "session_id" )
    private String sessionId;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column (name = "topic")
    private String topic;

    @Column(name = "duration_in_minutes")
    private int durationInMinutes;

    @Column (name= "start_datetime")
    private LocalDateTime startDateTime;

    @Column (name = "end_datetime")
    private LocalDateTime endDateTime;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "course_id",nullable = false)
    private Course course;

}

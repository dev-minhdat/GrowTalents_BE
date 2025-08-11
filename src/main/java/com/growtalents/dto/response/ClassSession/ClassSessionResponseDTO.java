package com.growtalents.dto.response.ClassSession;

import com.growtalents.model.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassSessionResponseDTO {
    private String sessionId;
    private LocalDate sessionDate;
    private String topic;
    private int durationInMinutes;
    private LocalTime startTime;
    private LocalTime endTime;
    private Course course;
}

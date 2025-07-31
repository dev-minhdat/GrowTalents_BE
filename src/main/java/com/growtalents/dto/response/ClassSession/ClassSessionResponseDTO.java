package com.growtalents.dto.response.ClassSession;

import com.growtalents.model.Course;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Course course;
}

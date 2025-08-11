package com.growtalents.dto.request.ClassSession;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassSessionCreateRequestDTO {
    private LocalDate sessionDate;
    private String topic;
    private int durationInMinutes;
    private LocalTime startTime;
    private LocalTime endTime;
    private String courseId;
}

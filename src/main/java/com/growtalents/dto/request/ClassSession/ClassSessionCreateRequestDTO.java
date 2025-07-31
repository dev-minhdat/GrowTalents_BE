package com.growtalents.dto.request.ClassSession;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassSessionCreateRequestDTO {
    private LocalDate sessionDate;
    private String topic;
    private int durationInMinutes;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String courseId;
}

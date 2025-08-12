package com.growtalents.dto.response.Student;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentClassSessionResponseDTO {
    private String sessionId;
    private LocalDate sessionDate;
    private String topic;
    private int durationInMinutes;
    private LocalTime startTime;
    private LocalTime endTime;
    private String courseId;
    private String courseName;
    private String courseType;
}

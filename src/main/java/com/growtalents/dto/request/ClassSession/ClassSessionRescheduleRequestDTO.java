package com.growtalents.dto.request.ClassSession;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassSessionRescheduleRequestDTO {
    private String sessionId;
    private LocalDate proposedDate;
    private LocalTime proposedStartTime;
    private String rescheduleReason;
}

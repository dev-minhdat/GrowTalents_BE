package com.growtalents.dto.response.Assignment;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AssignmentStudentStatusResponseDTO {
    private String assignmentId;
    private String title;
    private boolean submitted;
    private LocalDate deadline;
    private LocalDate createdAt;
}

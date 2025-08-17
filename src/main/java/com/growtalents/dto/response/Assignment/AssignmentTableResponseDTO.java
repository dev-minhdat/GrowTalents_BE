package com.growtalents.dto.response.Assignment;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentTableResponseDTO {
    private String assignmentId;
    private String title;
    private LocalDate deadline;
    private Long studentSubmittedCount;
}

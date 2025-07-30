package com.growtalents.dto.response.TeachingLog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeachingLogResponseDTO {
    private String logId;
    private String teacherId;
    private String sessionId;
    private double teachingHours;
    private Integer salaryAmount;
    private String requestChange;
    private String status;
}

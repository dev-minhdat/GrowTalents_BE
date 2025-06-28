package com.growtalents.dto.request.TeachingLog;

import lombok.Data;

@Data
public class TeachingLogCreateRequestDTO {
    private String teacherId;
    private String sessionId;
    private double teachingHours;
    private Integer salaryAmount;
    private String requestChange;
    private String status;
}

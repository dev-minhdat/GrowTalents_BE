package com.growtalents.dto.response.Semester;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SemesterResponseDTO {
    private String semesterId;
    private String name;
    private String startDate;
    private String endDate;
    private String status;
}

package com.growtalents.dto.request.Semester;

import lombok.Data;

@Data
public class SemesterCreateRequestDTO {
    private String name;
    private String startDate; // ISO format
    private String endDate; // ISO format
    private String status;
}

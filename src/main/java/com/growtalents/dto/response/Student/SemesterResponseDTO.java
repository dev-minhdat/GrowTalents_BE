package com.growtalents.dto.response.Student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterResponseDTO {
    private String semesterId;
    private String semesterName;
    private Integer year;
    private String status;
    private String startDate;
    private String endDate;
}

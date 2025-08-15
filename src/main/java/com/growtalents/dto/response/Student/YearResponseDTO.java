package com.growtalents.dto.response.Student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YearResponseDTO {
    private Integer year;
    private String description;
    private Integer totalSemesters;
}

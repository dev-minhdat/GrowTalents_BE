package com.growtalents.dto.response.Semester;

import com.growtalents.enums.SemesterStatus;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SemesterResponseDTO {
    private String semesterId;
    private Integer Year;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private SemesterStatus status;
}

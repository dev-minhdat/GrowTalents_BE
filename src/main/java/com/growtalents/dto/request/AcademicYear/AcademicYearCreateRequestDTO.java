package com.growtalents.dto.request.AcademicYear;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcademicYearCreateRequestDTO {
    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be greater than or equal to 2000")
    private Integer year;
}

package com.growtalents.dto.request.Semester;

import com.growtalents.enums.SemesterStatus;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SemesterCreateRequestDTO {
    private Integer yearId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
//    private SemesterStatus status; khong truyen nhung o serviceimpl nho xu ly
}

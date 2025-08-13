package com.growtalents.dto.request.Attendance;

import com.growtalents.enums.AttendanceStatus;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AttendanceUpdateRequestDTO {
    
    @NotBlank(message = "Attendance ID không được để trống")
    private String attendanceId;
    
    @NotNull(message = "Trạng thái điểm danh không được để trống")
    private AttendanceStatus status;
    
    private String note;
}
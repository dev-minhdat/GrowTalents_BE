package com.growtalents.dto.request.Attendance;

import com.growtalents.enums.AttendanceStatus;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AttendanceCreateRequestDTO {
    @NotBlank(message = "Course ID không được để trống")
    private String courseId;
    
    @NotBlank(message = "Ngày điểm danh không được để trống")
    private String date;
    
    @NotEmpty(message = "Danh sách điểm danh không được để trống")
    @Valid
    private List<AttendanceRecordRequestDTO> attendanceRecords;
    
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class AttendanceRecordRequestDTO {
        @NotBlank(message = "Student ID không được để trống")
        private String studentId;
        
        @NotNull(message = "Trạng thái điểm danh không được để trống")
        private AttendanceStatus status;
        
        private String note;
    }
}

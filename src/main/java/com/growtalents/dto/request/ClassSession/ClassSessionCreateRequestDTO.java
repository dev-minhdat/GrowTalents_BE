package com.growtalents.dto.request.ClassSession;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassSessionCreateRequestDTO {
    
    @NotNull(message = "Ngày học không được để trống")
    private LocalDate sessionDate;
    
    @NotBlank(message = "Chủ đề buổi học không được để trống")
    private String topic;
    
    @Min(value = 15, message = "Thời lượng buổi học tối thiểu 15 phút")
    private int durationInMinutes;
    
    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private LocalTime startTime;
    
    @NotBlank(message = "ID khóa học không được để trống")
    private String courseId;
}

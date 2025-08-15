package com.growtalents.dto.request.TeacherCourse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherCourseCreateRequestDTO {
    
    @NotBlank(message = "Vai trò phân công không được để trống")
    private String assignedRole;
    
    @NotNull(message = "Mức lương theo giờ không được để trống")
    @Min(value = 0, message = "Mức lương theo giờ phải >= 0")
    private Integer hourlyRate;
    
    @NotBlank(message = "ID giáo viên không được để trống")
    private String teacherId;
    
    @NotBlank(message = "ID khóa học không được để trống")
    private String courseId;
}

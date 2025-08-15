package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.TeacherCourse.TeacherCourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;

import java.util.List;

public interface TeacherCourseService {
    public List<CourseResponseDTO> getActiveCoursesByTeacher(String teacherId);
    
    /**
     * Thêm giáo viên vào khóa học
     * @param dto Thông tin assign giáo viên
     */
    public void assignTeacherToCourse(TeacherCourseCreateRequestDTO dto);
}

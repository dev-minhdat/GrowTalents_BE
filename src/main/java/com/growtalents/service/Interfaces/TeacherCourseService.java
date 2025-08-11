package com.growtalents.service.Interfaces;

import com.growtalents.dto.response.Course.CourseResponseDTO;

import java.util.List;

public interface TeacherCourseService {
    public List<CourseResponseDTO> getActiveCoursesByTeacher(String teacherId);
}

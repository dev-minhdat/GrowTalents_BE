package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Course.CourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;

import java.util.List;

public interface CourseService {
    CourseResponseDTO create (CourseCreateRequestDTO dto);
    CourseResponseDTO getById(String courseId);
    List<CourseResponseDTO> list(String keyword, CourseStatus status, CourseType type);
    CourseResponseDTO updateStatus(String courseId, CourseStatus status);
}

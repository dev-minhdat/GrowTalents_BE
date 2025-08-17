package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Teacher.TeacherCreateRequestDTO;
import com.growtalents.dto.response.Teacher.TeacherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {
    TeacherResponseDTO createTeacher(TeacherCreateRequestDTO dto);
    Page<TeacherResponseDTO> getAllTeachers(String keyword, Pageable pageable);
    TeacherResponseDTO getTeacherById(String teacherId);
}

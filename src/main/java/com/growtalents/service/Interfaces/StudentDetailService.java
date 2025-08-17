package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Student.StudentCreateRequestDTO;
import com.growtalents.dto.response.Student.StudentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentDetailService {
    StudentResponseDTO createStudent(StudentCreateRequestDTO dto);
    Page<StudentResponseDTO> getAllStudents(String keyword, Pageable pageable);
    StudentResponseDTO getStudentById(String studentId);
}

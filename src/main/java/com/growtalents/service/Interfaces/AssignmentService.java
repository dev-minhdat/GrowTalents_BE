package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;

import java.util.List;

public interface AssignmentService {
    public List<AssignmentResponseDTO> getAllAssignmentByLessonId(String lessonId);
    public void createAssignment(AssignmentCreateRequestDTO dto);
    public List<AssignmentResponseDTO> getAllAssignmentByTeacherId(String teacherId);
}

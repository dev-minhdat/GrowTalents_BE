package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.dto.response.Assignment.AssignmentStudentStatusResponseDTO;

import java.util.List;

public interface AssignmentService {
    public List<AssignmentResponseDTO> getAllAssignmentByLessonId(String lessonId);
    public void createAssignment(AssignmentCreateRequestDTO dto);
    public List<AssignmentResponseDTO> getAllAssignmentByTeacherId(String teacherId);
    // Danh sách bài tập đã hoàn thành va chua hoan thanh cho 1 khoa hoc cu the
    List<AssignmentStudentStatusResponseDTO> getAllAssignmentByStudentId(String studentId, String courseId);

    // tất cả course đã đăng ký
    List<AssignmentStudentStatusResponseDTO> getAllAssignmentByStudentIdAcrossCourses(String studentId);

}

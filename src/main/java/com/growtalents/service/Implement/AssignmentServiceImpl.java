package com.growtalents.service.Implement;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.dto.response.Assignment.AssignmentStudentStatusResponseDTO;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.AssignmentMapper;
import com.growtalents.model.Assignment;
import com.growtalents.model.Lesson;
import com.growtalents.repository.AssignmentRepository;
import com.growtalents.repository.LessonRepository;
import com.growtalents.repository.StudentSubmissionRepository;
import com.growtalents.service.Interfaces.AssignmentService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final StudentSubmissionRepository studentSubmissionRepository;
    private final AssignmentMapper assignmentMapper;
    private final LessonRepository lessonRepository;
    private final IdSequenceService idSequenceService;
    @Override
    public List<AssignmentResponseDTO> getAllAssignmentByLessonId(String lessonId) {
        List<Assignment> assignments = assignmentRepository.findByLesson_LessonId(lessonId);
        return assignmentMapper.toResponseDTO(assignments);
    }

    @Override
    public void createAssignment(AssignmentCreateRequestDTO dto) {
        Lesson lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with id: " + dto.getLessonId()));

        // Sinh index
        int index = idSequenceService.getNextIndex("Assignment");
        // Sinh ID theo định dạng
        String assignmentId = IdGenerator.generateAssignmentId(index);

        Assignment assignment= assignmentMapper.toEntity(dto,assignmentId,lesson);
        assignmentRepository.save(assignment);
    }

    @Override
    public List<AssignmentResponseDTO> getAllAssignmentByTeacherId(String teacherId) {
        List<Assignment> assignments = assignmentRepository.findByTeacherIdOrderByCreatedAtDesc(teacherId);
        return assignmentMapper.toResponseDTO(assignments);
    }

    @Override
    public List<AssignmentStudentStatusResponseDTO> getAllAssignmentByStudentId(String studentId, String courseId) {
        // Lấy toàn bộ assignment của course (đã lọc enrollment ở DB để đảm bảo an toàn)
        List<Assignment> assignments = assignmentRepository.findAssignmentsForStudentInCourse(studentId, courseId);

        // Lấy danh sách assignmentId mà student đã nộp trong course (1 query)
        Set<String> submittedIds = new HashSet<>(
                studentSubmissionRepository.findSubmittedAssignmentIdsInCourse(studentId, courseId)
        );

        // Map sang DTO kèm cờ submitted (O(n))
        return assignments.stream()
                .map(a -> AssignmentMapper.toStudentStatusDTO(a, submittedIds.contains(a.getAssignmentId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentStudentStatusResponseDTO> getAllAssignmentByStudentIdAcrossCourses(String studentId) {
        // Lấy assignments của tất cả course đã enroll
        List<Assignment> assignments = assignmentRepository.findAssignmentsForStudentAcrossCourses(studentId);

        // Lấy tất cả assignmentId đã nộp của student (1 query)
        Set<String> submittedIds = new HashSet<>(
                studentSubmissionRepository.findSubmittedAssignmentIdsForStudent(studentId)
        );

        return assignments.stream()
                .map(a -> AssignmentMapper.toStudentStatusDTO(a, submittedIds.contains(a.getAssignmentId())))
                .collect(Collectors.toList());
    }
}

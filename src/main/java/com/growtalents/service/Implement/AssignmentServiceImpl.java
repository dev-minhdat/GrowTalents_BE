package com.growtalents.service.Implement;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.AssignmentMapper;
import com.growtalents.model.Assignment;
import com.growtalents.model.Lesson;
import com.growtalents.repository.AssignmentRepository;
import com.growtalents.repository.LessonRepository;
import com.growtalents.service.Interfaces.AssignmentService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
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
}

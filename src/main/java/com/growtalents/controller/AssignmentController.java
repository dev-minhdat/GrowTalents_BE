package com.growtalents.controller;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping
    @Operation(summary = "Lấy danh sách bài tập theo LessonId0")
    public ResponseEntity<GlobalResponse<List<AssignmentResponseDTO>>> getAllAssignmentsByLessonId(
            @RequestParam("lessonId") String lessonId) {
        List<AssignmentResponseDTO> assignments = assignmentService.getAllAssignmentByLessonId(lessonId);
        return ResponseEntity.ok(GlobalResponse.success(assignments));
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Void>> createAssignment(
            @Valid @RequestBody AssignmentCreateRequestDTO dto) {
        assignmentService.createAssignment(dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo bài tập thành công", null));
    }
}

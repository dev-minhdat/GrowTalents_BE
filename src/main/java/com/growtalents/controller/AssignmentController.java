package com.growtalents.controller;

import com.growtalents.dto.request.Assignment.AssignmentCreateRequestDTO;
import com.growtalents.dto.response.Assignment.AssignmentResponseDTO;
import com.growtalents.dto.response.Assignment.AssignmentStudentStatusResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignments")
@Tag(name = "Assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/by-lesson")
    @Operation(summary = "Lấy danh sách bài tập theo LessonId")
    public ResponseEntity<GlobalResponse<List<AssignmentResponseDTO>>> getAllAssignmentsByLessonId(
            @Parameter(description = "ID của Lesson", required = true)
            @RequestParam("lessonId") String lessonId) {
        List<AssignmentResponseDTO> assignments = assignmentService.getAllAssignmentByLessonId(lessonId);
        return ResponseEntity.ok(GlobalResponse.success(assignments));
    }

    @PostMapping
    @Operation(summary = "Tạo bài tập mới")
    public ResponseEntity<GlobalResponse<Void>> createAssignment(
            @Valid @RequestBody AssignmentCreateRequestDTO dto) {
        assignmentService.createAssignment(dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo bài tập thành công", null));
    }

    @GetMapping("/by-teacher")
    @Operation(summary = "Lấy danh sách bài tập theo TeacherId")
    public ResponseEntity<GlobalResponse<List<AssignmentResponseDTO>>> getAllAssignmentsByTeacherId(
            @Parameter(description = "ID của Teacher", required = true)
            @RequestParam String teacherId ) {
        List<AssignmentResponseDTO> assignments = assignmentService.getAllAssignmentByTeacherId(teacherId);
        return ResponseEntity.ok(GlobalResponse.success(assignments));
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    @Operation(summary = "Danh sách bài tập của một khóa cho một học sinh (kèm cờ submitted)")
    public ResponseEntity<GlobalResponse<List<AssignmentStudentStatusResponseDTO>>> getAssignmentsInCourseForStudent(
            @Parameter(description = "ID học sinh", required = true)
            @PathVariable String studentId,
            @Parameter(description = "ID khóa học", required = true)
            @PathVariable String courseId
    ) {
        var data = assignmentService.getAllAssignmentByStudentId(studentId, courseId);
        return ResponseEntity.ok(GlobalResponse.success("Fetched assignments in course", data));
    }

    @GetMapping("/students/{studentId}/all")
    @Operation(summary = "Danh sách bài tập của tất cả các khóa đã đăng ký (kèm cờ submitted)")
    public ResponseEntity<GlobalResponse<List<AssignmentStudentStatusResponseDTO>>> getAssignmentsAcrossCoursesForStudent(
            @Parameter(description = "ID học sinh", required = true)
            @PathVariable String studentId
    ) {
        var data = assignmentService.getAllAssignmentByStudentIdAcrossCourses(studentId);
        return ResponseEntity.ok(GlobalResponse.success("Fetched assignments across courses", data));
    }
}

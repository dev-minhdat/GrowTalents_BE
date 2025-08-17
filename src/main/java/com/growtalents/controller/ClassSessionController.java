package com.growtalents.controller;

import com.growtalents.dto.request.ClassSession.ClassSessionCreateRequestDTO;
import com.growtalents.dto.request.ClassSession.ClassSessionRescheduleRequestDTO;
import com.growtalents.dto.response.ClassSession.ClassSessionResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.ClassSessionService;
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
@RequestMapping("/api/sessions")
@Tag(name = "Class Session APIs", description = "APIs quản lý buổi học")
public class ClassSessionController {
    private final ClassSessionService service;

    // Giáo viên gửi đề xuất
    @PostMapping("/{sessionId}/reschedule")
    public ResponseEntity<GlobalResponse<Void>> requestReschedule(
            @PathVariable String sessionId,
            @RequestParam String teacherId, // thực tế nên lấy từ auth
            @RequestBody ClassSessionRescheduleRequestDTO dto
    ) {
        dto.setSessionId(sessionId); // đồng bộ path/body
        service.requestReschedule(teacherId, dto);
        return ResponseEntity.ok(GlobalResponse.success(null));
    }

    // Admin duyệt
    @PostMapping("/{sessionId}/reschedule/approve")
    public ResponseEntity<GlobalResponse<Void>> approve(@PathVariable String sessionId) {
        service.approveReschedule(sessionId);
        return ResponseEntity.ok(GlobalResponse.success(null));
    }

    // Admin từ chối
    @PostMapping("/{sessionId}/reschedule/reject")
    public ResponseEntity<GlobalResponse<Void>> reject(
            @PathVariable String sessionId,
            @RequestParam(required = false) String reason
    ) {
        service.rejectReschedule(sessionId, reason);
        return ResponseEntity.ok(GlobalResponse.success(null));
    }
    // Lấy danh sách reschedule của 1 giáo viên
    @GetMapping("/reschedules")
    public ResponseEntity<GlobalResponse<?>> getReschedules(@RequestParam String teacherId) {
        var data = service.getAllRescheduled(teacherId);
        return ResponseEntity.ok(GlobalResponse.success(data));
    }

    // Tạo buổi học mới cho giáo viên
    @Operation(
        summary = "Tạo buổi học mới",
        description = "Giáo viên tạo buổi học mới cho khóa học mình được phân công. " +
                     "Thời gian kết thúc sẽ được tự động tính từ thời gian bắt đầu + thời lượng."
    )
    @PostMapping("/create")
    public ResponseEntity<GlobalResponse<Void>> createClassSession(
            @Parameter(description = "ID của giáo viên", required = true)
            @RequestParam String teacherId,
            @Parameter(description = "Thông tin buổi học mới", required = true)
            @Valid @RequestBody ClassSessionCreateRequestDTO dto) {
        
        service.createClassSession(teacherId, dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo buổi học thành công", null));
    }

    // Lấy danh sách buổi học của giáo viên
    @Operation(
        summary = "Lấy danh sách buổi học của giáo viên",
        description = "Lấy tất cả buổi học mà giáo viên đang phụ trách"
    )
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<GlobalResponse<List<ClassSessionResponseDTO>>> getTeacherClassSessions(
            @Parameter(description = "ID của giáo viên", required = true)
            @PathVariable String teacherId) {
        
        List<ClassSessionResponseDTO> sessions = service.getTeacherClassSessions(teacherId);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách buổi học thành công", sessions));
    }
}

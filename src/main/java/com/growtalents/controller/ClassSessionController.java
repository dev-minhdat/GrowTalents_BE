package com.growtalents.controller;

import com.growtalents.dto.request.ClassSession.ClassSessionRescheduleRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.ClassSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sessions")
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
}

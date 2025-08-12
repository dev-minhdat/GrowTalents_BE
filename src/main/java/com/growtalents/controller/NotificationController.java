package com.growtalents.controller;

import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Notification.NotificationResponseDTO;
import com.growtalents.service.Interfaces.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/students/{studentId}")
    @Operation(summary = "Danh sách thông báo của học sinh")
    public ResponseEntity<GlobalResponse<List<NotificationResponseDTO>>> getStudentNotifications(
            @PathVariable String studentId) {
        var data = notificationService.getStudentNotifications(studentId);
        return ResponseEntity.ok(GlobalResponse.success(data));
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    @Operation(summary = "Danh sách thông báo của học sinh theo khóa học")
    public ResponseEntity<GlobalResponse<List<NotificationResponseDTO>>> getStudentNotificationsByCourse(
            @PathVariable String studentId,
            @PathVariable String courseId) {
        var data = notificationService.getStudentNotificationsByCourse(studentId, courseId);
        return ResponseEntity.ok(GlobalResponse.success(data));
    }
}

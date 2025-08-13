package com.growtalents.controller;

import com.growtalents.service.Implement.AttendanceServiceImpl;
import com.growtalents.service.Interfaces.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AttendanceController {
    private final AttendanceServiceImpl attendanceService;

    /**
     * Level A: Get danh sách lớp theo ID giáo viên
     * GET /api/attendance/teacher/{teacherId}/classes
     */
    @GetMapping("/teacher/{teacherId}/classes")
    public ResponseEntity<List<Map<String, Object>>> getTeacherClasses(
            @PathVariable String teacherId) {
        try {
            List<Map<String, Object>> classes = attendanceService.getTeacherClasses(teacherId);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Level B: Create điểm danh mới
     * POST /api/attendance
     */
    @PostMapping
    public ResponseEntity<String> createAttendance(
            @RequestBody Map<String, Object> request) {
        try {
            System.out.println("=== CREATE ATTENDANCE REQUEST ===");
            System.out.println("Request: " + request);

            String result = attendanceService.createAttendance(request);

            System.out.println("=== CREATE ATTENDANCE SUCCESS ===");
            System.out.println("Result: " + result);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            System.out.println("=== ILLEGAL ARGUMENT ERROR ===");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println("=== GENERAL ERROR ===");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra khi tạo điểm danh: " + e.getMessage());
        }
    }
    /**
     * Level B: Update điểm danh
     * PUT /api/attendance/{attendanceId}
     */
    @PutMapping("/{attendanceId}")
    public ResponseEntity<String> updateAttendance(
            @PathVariable String attendanceId,
            @RequestBody Map<String, Object> request) {
        try {
            String result = attendanceService.updateAttendance(attendanceId, request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra khi cập nhật điểm danh");
        }
    }

    /**
     * Level C: Report lịch sử điểm danh theo giáo viên
     * GET /api/attendance/teacher/{teacherId}/history
     */
    @GetMapping("/teacher/{teacherId}/history")
    public ResponseEntity<List<Map<String, Object>>> getAttendanceHistory(
            @PathVariable String teacherId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        try {
            List<Map<String, Object>> history = attendanceService.getAttendanceHistory(
                    teacherId, startDate, endDate);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Bonus: Get điểm danh theo buổi học cụ thể
     * GET /api/attendance/session/{sessionId}
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<Map<String, Object>>> getAttendanceBySession(
            @PathVariable String sessionId) {
        try {
            List<Map<String, Object>> attendances = attendanceService.getAttendanceBySession(sessionId);
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Bonus: Get điểm danh theo khóa học và ngày
     * GET /api/attendance/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Map<String, Object>>> getAttendanceByCourse(
            @PathVariable String courseId,
            @RequestParam LocalDate date) {
        try {
            List<Map<String, Object>> attendances = attendanceService.getAttendanceByCourse(courseId, date);
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.growtalents.controller;

import com.growtalents.dto.request.AppUser.AppUserCreateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Student.*;
import com.growtalents.service.Implement.StudentServiceImpl;
import com.growtalents.service.Interfaces.AppUserService;
import com.growtalents.service.Interfaces.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student APIs", description = "APIs dành cho học sinh")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final AppUserService appUserService;

    @Operation(
        summary = "Lấy danh sách tất cả học sinh",
        description = "Lấy danh sách tất cả học sinh trong hệ thống"
    )
    @GetMapping
    public ResponseEntity<GlobalResponse<List<StudentListResponseDTO>>> getAllStudents() {
        List<StudentListResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách học sinh thành công", students));
    }

    @Operation(
        summary = "Tạo học sinh mới",
        description = "Tạo tài khoản học sinh mới trong hệ thống"
    )
    @PostMapping
    public ResponseEntity<GlobalResponse<Void>> createStudent(
            @Parameter(description = "Thông tin học sinh mới", required = true)
            @Valid @RequestBody AppUserCreateRequestDTO dto) {
        
        // Đặt role mặc định là STUDENT
        dto.setUserRole(com.growtalents.enums.UserRole.STUDENT);
        appUserService.addAppUser(dto);
        
        return ResponseEntity.ok(GlobalResponse.success("Tạo học sinh thành công", null));
    }

    @Operation(
        summary = "Lấy danh sách năm học",
        description = "Lấy danh sách tất cả năm học có trong hệ thống"
    )
    @GetMapping("/years")
    public ResponseEntity<GlobalResponse<List<YearResponseDTO>>> getAllYears() {
        List<YearResponseDTO> years = studentService.getAllYears();
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách năm học thành công", years));
    }

    @Operation(
        summary = "Lấy danh sách kỳ học theo năm",
        description = "Lấy danh sách các kỳ học trong một năm học cụ thể"
    )
    @GetMapping("/years/{year}/semesters")
    public ResponseEntity<GlobalResponse<List<SemesterResponseDTO>>> getSemestersByYear(
            @Parameter(description = "Năm học", required = true, example = "2024")
            @PathVariable Integer year) {
        
        List<SemesterResponseDTO> semesters = studentService.getSemestersByYear(year);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách kỳ học thành công", semesters));
    }

    @Operation(
        summary = "Lấy danh sách lớp theo kỳ học",
        description = "Lấy danh sách các lớp học trong một kỳ học cụ thể"
    )
    @GetMapping("/semesters/{semesterId}/classes")
    public ResponseEntity<GlobalResponse<List<ClassResponseDTO>>> getClassesBySemester(
            @Parameter(description = "ID kỳ học", required = true, example = "SEM_001")
            @PathVariable String semesterId) {
        
        List<ClassResponseDTO> classes = studentService.getClassesBySemester(semesterId);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách lớp học thành công", classes));
    }

    @Operation(
        summary = "Lấy danh sách buổi học trong tuần",
        description = "Lấy danh sách buổi học theo định dạng ngày đầu và cuối (dd/MM/yyyy)"
    )
    @GetMapping("/{studentId}/class-sessions")
    public ResponseEntity<GlobalResponse<List<StudentClassSessionResponseDTO>>> getWeeklyClassSessions(
            @Parameter(description = "ID của học sinh", required = true, example = "USR_001")
            @PathVariable String studentId,
            @Parameter(description = "Ngày bắt đầu (dd/MM/yyyy)", required = true, example = "01/01/2025")
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @Parameter(description = "Ngày kết thúc (dd/MM/yyyy)", required = true, example = "07/01/2025")
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        
        // Validate date range
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest()
                    .body(GlobalResponse.error("Ngày bắt đầu không thể sau ngày kết thúc", 400));
        }
        
        List<StudentClassSessionResponseDTO> sessions = studentService
                .getWeeklyClassSessions(studentId, startDate, endDate);
        
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách buổi học thành công", sessions));
    }

    @Operation(
        summary = "Lấy danh sách khóa học đã đăng ký",
        description = "Lấy danh sách tất cả khóa học mà học sinh đã đăng ký"
    )
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<GlobalResponse<List<StudentCourseResponseDTO>>> getEnrolledCourses(
            @Parameter(description = "ID của học sinh", required = true, example = "USR_001")
            @PathVariable String studentId) {
        
        List<StudentCourseResponseDTO> courses = studentService.getEnrolledCourses(studentId);
        
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách khóa học thành công", courses));
    }

    @Operation(
        summary = "Lấy danh sách bài tập theo thứ tự mới nhất",
        description = "Lấy danh sách bài tập hiện có trong khóa học theo thứ tự mới nhất"
    )
    @GetMapping("/{studentId}/assignments")
    public ResponseEntity<GlobalResponse<List<StudentAssignmentResponseDTO>>> getAssignments(
            @Parameter(description = "ID của học sinh", required = true, example = "USR_001")
            @PathVariable String studentId) {
        
        List<StudentAssignmentResponseDTO> assignments = studentService.getAssignmentsByStudent(studentId);
        
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách bài tập thành công", assignments));
    }

    @Operation(
        summary = "Lấy thống kê học sinh",
        description = "Lấy thống kê bao gồm: Điểm trung bình, bài kiểm tra đã làm, bài kiểm tra chưa làm"
    )
    @GetMapping("/{studentId}/statistics")
    public ResponseEntity<GlobalResponse<StudentStatisticsResponseDTO>> getStudentStatistics(
            @Parameter(description = "ID của học sinh", required = true, example = "USR_001")
            @PathVariable String studentId) {
        
        StudentStatisticsResponseDTO statistics = studentService.getStudentStatistics(studentId);
        
        return ResponseEntity.ok(GlobalResponse.success("Lấy thống kê học sinh thành công", statistics));
    }

    @Operation(
        summary = "Lấy nhận xét của giáo viên",
        description = "Lấy comment của giáo viên cho các phần bài tập mà student đã làm"
    )
    @GetMapping("/{studentId}/teacher-comments")
    public ResponseEntity<GlobalResponse<List<StudentGradeCommentResponseDTO>>> getTeacherComments(
            @Parameter(description = "ID của học sinh", required = true, example = "USR_001")
            @PathVariable String studentId) {
        
        List<StudentGradeCommentResponseDTO> comments = studentService.getTeacherComments(studentId);
        
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách nhận xét thành công", comments));
    }
}

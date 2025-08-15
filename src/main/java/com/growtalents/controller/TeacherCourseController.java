package com.growtalents.controller;

import com.growtalents.dto.request.TeacherCourse.TeacherCourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.TeacherCourseService;
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
@RequestMapping("/api/teachercourses")
@Tag(name = "Teacher Course Management", description = "APIs for managing teacher-course assignments")
public class TeacherCourseController {

    private final TeacherCourseService teacherCourseService;

    @Operation(
        summary = "Lấy danh sách khóa học của giáo viên",
        description = "Lấy tất cả khóa học đang hoạt động mà giáo viên được phân công"
    )
    @GetMapping("/{teacherId}")
    public ResponseEntity<GlobalResponse<List<CourseResponseDTO>>> getTeacherCourses(
            @Parameter(description = "ID của giáo viên", required = true, example = "TCH_001")
            @PathVariable String teacherId
    ) {
        List<CourseResponseDTO> lists = teacherCourseService.getActiveCoursesByTeacher(teacherId);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách khóa học thành công", lists));
    }
    
    @Operation(
        summary = "Thêm giáo viên vào khóa học",
        description = "Phân công giáo viên vào khóa học với vai trò và mức lương được chỉ định"
    )
    @PostMapping
    public ResponseEntity<GlobalResponse<Void>> assignTeacherToCourse(
            @Parameter(description = "Thông tin phân công giáo viên", required = true)
            @Valid @RequestBody TeacherCourseCreateRequestDTO dto) {
        teacherCourseService.assignTeacherToCourse(dto);
        return ResponseEntity.ok(GlobalResponse.success("Phân công giáo viên vào khóa học thành công", null));
    }
}

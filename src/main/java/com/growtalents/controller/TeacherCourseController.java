package com.growtalents.controller;

import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.TeacherCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachercourses")
public class TeacherCourseController {

    private final TeacherCourseService teacherCourseService;

    @GetMapping("/{teacherId}")
    public ResponseEntity<GlobalResponse<List<CourseResponseDTO>>> getTeacherCourses(
            @PathVariable String teacherId
    ) {
        List<CourseResponseDTO> lists = teacherCourseService.getActiveCoursesByTeacher(teacherId);
        return ResponseEntity.ok(GlobalResponse.success(lists));
    }
}

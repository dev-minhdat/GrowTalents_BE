package com.growtalents.controller;

import com.growtalents.dto.request.Teacher.TeacherCreateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Teacher.TeacherResponseDTO;
import com.growtalents.service.Interfaces.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {
    
    private final TeacherService teacherService;
    
    @PostMapping
    public ResponseEntity<GlobalResponse<TeacherResponseDTO>> createTeacher(@RequestBody TeacherCreateRequestDTO dto) {
        TeacherResponseDTO result = teacherService.createTeacher(dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo giáo viên thành công", result));
    }
    
    @GetMapping
    public ResponseEntity<GlobalResponse<Page<TeacherResponseDTO>>> getAllTeachers(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<TeacherResponseDTO> result = teacherService.getAllTeachers(keyword, pageable);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách giáo viên thành công", result));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<TeacherResponseDTO>> getTeacherById(@PathVariable String id) {
        TeacherResponseDTO result = teacherService.getTeacherById(id);
        return ResponseEntity.ok(GlobalResponse.success("Lấy thông tin giáo viên thành công", result));
    }
}

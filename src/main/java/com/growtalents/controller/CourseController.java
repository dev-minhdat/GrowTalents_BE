package com.growtalents.controller;

import com.growtalents.dto.request.Course.CourseCreateRequestDTO;
import com.growtalents.dto.request.Course.CourseUpdateStatusRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import com.growtalents.service.Interfaces.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<GlobalResponse<CourseResponseDTO>> create(@RequestBody CourseCreateRequestDTO dto) {
        CourseResponseDTO result = courseService.create(dto);
        return ResponseEntity.ok(GlobalResponse.success("Created Successfully", result));
    }
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<CourseResponseDTO>> getById(@PathVariable String id) {
        CourseResponseDTO data = courseService.getById(id);
        return ResponseEntity.ok(GlobalResponse.success("OK", data));
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<CourseResponseDTO>>> list(
            @RequestParam(value = "q", required = false) String keyword,
            @RequestParam(value = "status", required = false) CourseStatus status,
            @RequestParam(value = "type", required = false) CourseType type
    ) {
        List<CourseResponseDTO> data = courseService.list(keyword, status, type);
        return ResponseEntity.ok(GlobalResponse.success("OK", data));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<GlobalResponse<CourseResponseDTO>> updateStatus(
            @PathVariable String id,
            @RequestBody CourseUpdateStatusRequestDTO req
    ) {
        CourseResponseDTO data = courseService.updateStatus(id, req.getStatus());
        return ResponseEntity.ok(GlobalResponse.success("Status updated", data));
    }
}

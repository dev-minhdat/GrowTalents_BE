package com.growtalents.controller;

import com.growtalents.dto.request.Syllabus.SyllabusCreateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Syllabus.SyllabusResponseDTO;
import com.growtalents.service.Interfaces.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/syllabus")
public class SyllabusController {
    private final SyllabusService syllabusService;
    // GET /api/syllabus?courseId=COURSE_001
    @GetMapping
    public ResponseEntity<GlobalResponse<SyllabusResponseDTO>> getSyllabus(
            @RequestParam String courseId) {
        SyllabusResponseDTO dto = syllabusService.getSyllabus(courseId);
        return ResponseEntity.ok(GlobalResponse.success("Get syllabus success", dto));
    }

    // POST /api/syllabus
    @PostMapping
    public ResponseEntity<GlobalResponse<SyllabusResponseDTO>> createSyllabus(
            @RequestBody SyllabusCreateRequestDTO requestDTO) {
        SyllabusResponseDTO dto = syllabusService.createSyllabus(requestDTO);
        return ResponseEntity.ok(GlobalResponse.success("Create syllabus success", dto));
    }
}

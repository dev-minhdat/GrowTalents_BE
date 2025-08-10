package com.growtalents.controller;

import com.growtalents.dto.request.Grade.GradeUpdateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/grades")
public class GradeController {
    private final GradeService gradeService;
    @PutMapping
    public ResponseEntity<GlobalResponse<Void>> updateScore(@RequestBody GradeUpdateRequestDTO dto) {
        gradeService.updateScore(dto);
        return ResponseEntity.ok(GlobalResponse.success(null)); // Không có data
    }
}

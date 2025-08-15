package com.growtalents.controller;

import com.growtalents.dto.request.Semester.SemesterCreateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Semester.SemesterResponseDTO;
import com.growtalents.service.Interfaces.SemesterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/semesters")
public class SemesterController {
    private final SemesterService semesterService;

    @PostMapping
    @Operation(summary = "Tạo học kỳ")
    public ResponseEntity<GlobalResponse<String>> createSemester(@RequestBody SemesterCreateRequestDTO dto) {
        semesterService.createSemester(dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo học kỳ thành công"));
    }

    @GetMapping("/year/{year}")
    @Operation(summary = "Lấy danh sách kỳ học theo năm")
    public ResponseEntity<GlobalResponse<List<SemesterResponseDTO>>> getByYear(@PathVariable int year) {
        return ResponseEntity.ok(GlobalResponse.success(semesterService.getSemesterByYear(year)));
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả các kỳ học")
    public ResponseEntity<GlobalResponse<List<SemesterResponseDTO>>> getAll() {
        return ResponseEntity.ok(GlobalResponse.success(semesterService.getAllSemesters()));
    }
}


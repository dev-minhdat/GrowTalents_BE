package com.growtalents.controller;

import com.growtalents.dto.request.AcademicYear.AcademicYearCreateRequestDTO;
import com.growtalents.dto.response.AcademicYear.AcademicYearResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.model.AcademicYear;
import com.growtalents.service.Interfaces.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/years")
public class AcademicYearController {
    private final AcademicYearService academicYearService;
    @GetMapping
    public ResponseEntity<GlobalResponse<List<AcademicYearResponseDTO>>> getAllYears() {
        List<AcademicYearResponseDTO> years = academicYearService.getAllAcademicYears();
        return ResponseEntity.ok(GlobalResponse.success(years));
    }
    @PostMapping
    public ResponseEntity<GlobalResponse> createYear(@RequestBody AcademicYearCreateRequestDTO dto) {
        academicYearService.createAcademicYear(dto);
        return ResponseEntity.ok(GlobalResponse.<String>success("Đăng ký năm thành công"));
    }
}

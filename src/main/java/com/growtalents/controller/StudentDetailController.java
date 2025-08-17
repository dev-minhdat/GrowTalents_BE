package com.growtalents.controller;

import com.growtalents.dto.request.Student.StudentCreateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Student.StudentResponseDTO;
import com.growtalents.service.Interfaces.StudentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student-details")
public class StudentDetailController {
    
    private final StudentDetailService studentDetailService;
    
    @PostMapping
    public ResponseEntity<GlobalResponse<StudentResponseDTO>> createStudent(@RequestBody StudentCreateRequestDTO dto) {
        StudentResponseDTO result = studentDetailService.createStudent(dto);
        return ResponseEntity.ok(GlobalResponse.success("Tạo học sinh thành công", result));
    }
    
    @GetMapping
    public ResponseEntity<GlobalResponse<Page<StudentResponseDTO>>> getAllStudents(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        
        Page<StudentResponseDTO> result = studentDetailService.getAllStudents(keyword, pageable);
        return ResponseEntity.ok(GlobalResponse.success("Lấy danh sách học sinh thành công", result));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<StudentResponseDTO>> getStudentById(@PathVariable String id) {
        StudentResponseDTO result = studentDetailService.getStudentById(id);
        return ResponseEntity.ok(GlobalResponse.success("Lấy thông tin học sinh thành công", result));
    }
}

package com.growtalents.controller;

import com.growtalents.dto.request.Document.DocumentCreateRequestDTO;
import com.growtalents.dto.response.Document.DocumentResponseDTO;
import com.growtalents.dto.response.Document.DocumentTypeResponse;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    @GetMapping("/types")
    @Operation(summary = "Lấy danh sách loại tài liệu (DocumentType)")
    public ResponseEntity<GlobalResponse<List<DocumentTypeResponse>>> getAllDocumentTypes() {
        List<DocumentTypeResponse> list = documentService.getAllTypes();
        return ResponseEntity.ok(GlobalResponse.success(list));
    }
    @PostMapping
    public ResponseEntity<GlobalResponse<DocumentResponseDTO>> createDocument(@RequestBody @Valid DocumentCreateRequestDTO dto) {
        DocumentResponseDTO docs = documentService.createDocument(dto);
        return ResponseEntity.ok(GlobalResponse.success("Document created successfully", docs));
    }
    @GetMapping
    @Operation(summary = "Lấy tất cả tài liệu theo lessonId")
    public ResponseEntity<GlobalResponse<List<DocumentResponseDTO>>> getDocumentsByLesson(
            @RequestParam String lessonId) {
        List<DocumentResponseDTO> documents = documentService.getAllDocuments(lessonId);
        return ResponseEntity.ok(GlobalResponse.success(documents));
    }
}

package com.growtalents.controller;

import com.growtalents.dto.response.Document.DocumentTypeResponse;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    @GetMapping("/types")
    @Operation(summary = "Lấy danh sách loại tài liệu (DocumentType)")
    public ResponseEntity<GlobalResponse<List<DocumentTypeResponse>>> getAllTypes() {
        List<DocumentTypeResponse> list = documentService.getAllTypes();
        return ResponseEntity.ok(GlobalResponse.success(list));
    }
}

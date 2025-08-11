package com.growtalents.mapper;

import com.growtalents.dto.request.Document.DocumentCreateRequestDTO;
import com.growtalents.dto.response.Document.DocumentResponseDTO;
import com.growtalents.model.Document;
import com.growtalents.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentMapper {
    public Document toEntity(DocumentCreateRequestDTO dto, String generateId, Lesson lesson) {
        return Document.builder()
                .documentId(generateId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .fileUrl(dto.getFileUrl())
                .uploadedAt(dto.getUploadedAt())
                .lesson(lesson)
                .build();
    }
    public DocumentResponseDTO toResponseDTO(Document document) {
        return DocumentResponseDTO.builder()
                .documentId(document.getDocumentId())
                .title(document.getTitle())
                .description(document.getDescription())
                .fileUrl(document.getFileUrl())
                .uploadedAt(document.getUploadedAt())
                .lesson(document.getLesson())
                .build();
    }
    public List<DocumentResponseDTO> toResponseDTO(List<Document> documents) {
        return documents.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}

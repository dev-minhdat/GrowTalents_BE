package com.growtalents.service.Implement;

import com.growtalents.dto.request.Document.DocumentCreateRequestDTO;
import com.growtalents.dto.response.Document.DocumentResponseDTO;
import com.growtalents.dto.response.Document.DocumentTypeResponse;
import com.growtalents.enums.DocumentType;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.DocumentMapper;
import com.growtalents.model.Document;
import com.growtalents.model.Lesson;
import com.growtalents.repository.DocumentRepository;
import com.growtalents.repository.LessonRepository;
import com.growtalents.service.Interfaces.DocumentService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final LessonRepository lessonRepository;
    private final IdSequenceService idSequenceService;
    @Override
    public List<DocumentTypeResponse> getAllTypes() {
        return Arrays.stream(DocumentType.values())
                .map(type -> new DocumentTypeResponse(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDTO> getAllDocuments(String lessonId) {
        List<Document> documents = documentRepository.findByLesson_LessonId(lessonId);
        return documentMapper.toResponseDTO(documents);
    }

    @Override
    public DocumentResponseDTO createDocument(DocumentCreateRequestDTO dto) {
        Lesson lesson = lessonRepository.findById(dto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        int index = idSequenceService.getNextIndex("Document");
        String documentId = IdGenerator.generateDocumentId(index);

        if (dto.getUploadedAt() == null) {
            dto.setUploadedAt(LocalDate.now());
        }

        Document document = documentMapper.toEntity(dto, documentId, lesson);
        documentRepository.save(document);

        return documentMapper.toResponseDTO(document);
    }
}

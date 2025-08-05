package com.growtalents.service.Implement;

import com.growtalents.dto.response.Document.DocumentTypeResponse;
import com.growtalents.enums.DocumentType;
import com.growtalents.service.Interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentImpl implements DocumentService {
    @Override
    public List<DocumentTypeResponse> getAllTypes() {
        return Arrays.stream(DocumentType.values())
                .map(type -> new DocumentTypeResponse(type))
                .collect(Collectors.toList());
    }
}

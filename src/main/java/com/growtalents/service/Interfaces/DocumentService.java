package com.growtalents.service.Interfaces;

import com.growtalents.dto.response.Document.DocumentTypeResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DocumentService {
    List<DocumentTypeResponse> getAllTypes();
}

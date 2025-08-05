package com.growtalents.dto.response.Document;

import com.growtalents.enums.DocumentType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentTypeResponse {
    DocumentType documentType;
}

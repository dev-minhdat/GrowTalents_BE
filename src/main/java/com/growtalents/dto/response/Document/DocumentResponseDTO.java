package com.growtalents.dto.response.Document;

import com.growtalents.model.Lesson;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentResponseDTO {
    private String documentId;
    private String title;
    private String description;
    private String fileUrl;
    private LocalDate uploadedAt;
    private Lesson lesson;
}

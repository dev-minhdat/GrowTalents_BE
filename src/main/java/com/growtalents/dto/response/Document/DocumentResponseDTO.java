package com.growtalents.dto.response.Document;

import com.growtalents.model.Lesson;
import lombok.*;

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
    private LocalDateTime uploadedAt;
    private Lesson lesson;
}

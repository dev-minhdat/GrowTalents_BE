package com.growtalents.dto.request.Document;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentCreateRequestDTO {
    private String lessonId;
    private String title;
    private String description;
    private String fileUrl;
    private LocalDateTime uploadedAt;
}

package com.growtalents.dto.request.Material;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MaterialCreateRequestDTO {
    private String courseId;
    private String title;
    private String description;
    private String fileUrl;
    private String uploadedByUserId;
    private LocalDateTime uploadDate;
}

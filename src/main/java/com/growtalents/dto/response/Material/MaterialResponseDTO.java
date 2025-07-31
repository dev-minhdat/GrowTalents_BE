package com.growtalents.dto.response.Material;

import com.growtalents.model.Course;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MaterialResponseDTO {
    private String materialId;
    private String title;
    private String description;
    private String fileUrl;
    private String uploadedByUserId;
    private String uploadedByUserName;
    private LocalDateTime uploadDate;
    private Course course;
}

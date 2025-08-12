package com.growtalents.dto.response.Material;

import com.growtalents.enums.MaterialType;
import com.growtalents.model.Course;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MaterialResponseDTO {
    private String materialId;
    private String courseId;
    private MaterialType type;
    private String content;
}

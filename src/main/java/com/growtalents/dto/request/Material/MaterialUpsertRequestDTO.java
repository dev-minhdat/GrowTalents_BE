package com.growtalents.dto.request.Material;

import com.growtalents.enums.MaterialType;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MaterialUpsertRequestDTO {
    private String courseId;
    private MaterialType type;   // COURSE_DESCRIPTION / LEARNING_OBJECTIVES / TEACHER_INFO
    private String content;      // rich text / markdown
}

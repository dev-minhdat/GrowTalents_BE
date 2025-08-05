package com.growtalents.dto.request.Syllabus;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SyllabusCreateRequestDTO {
    private String courseId;
    private String title;
}

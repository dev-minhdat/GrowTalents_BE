package com.growtalents.dto.response.Syllabus;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SyllabusResponseDTO {
    private String syllabusId;
    private String title;
    private String courseId;
}

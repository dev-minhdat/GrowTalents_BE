package com.growtalents.dto.response.Chapter;

import com.growtalents.model.Syllabus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChapterResponseDTO {
    private String chapterId;
    private Syllabus syllabus;
    private String title;
    private String description;
}

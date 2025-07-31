package com.growtalents.dto.request.Chapter;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChapterCreateRequestDTO {
    private String syllabusId;
    private String title;
    private String description;
}

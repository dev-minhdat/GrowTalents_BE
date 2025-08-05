package com.growtalents.dto.response.Lesson;

import com.growtalents.model.Chapter;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonResponseDTO {
    private String lessonId;
    private String title;
    private String content;
//    private Chapter chapter;
}

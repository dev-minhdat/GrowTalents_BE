package com.growtalents.dto.request.Lesson;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonCreateRequestDTO {
    private String chapterId;
    private String title;
    private String content;
}

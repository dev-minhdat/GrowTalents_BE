package com.growtalents.dto.response.SessionLesson;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionLessonResponseDTO {
    private String id;
    private String sessionId;
    private String lessonId;
    private String note;
}

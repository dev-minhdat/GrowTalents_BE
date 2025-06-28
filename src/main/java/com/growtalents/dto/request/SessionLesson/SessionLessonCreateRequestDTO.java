package com.growtalents.dto.request.SessionLesson;

import lombok.Data;

@Data
public class SessionLessonCreateRequestDTO {
    private String sessionId;
    private String lessonId;
    private String note;
}

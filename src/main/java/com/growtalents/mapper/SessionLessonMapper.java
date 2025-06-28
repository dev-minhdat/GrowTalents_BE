package com.growtalents.mapper;

import com.growtalents.dto.request.SessionLesson.SessionLessonCreateRequestDTO;
import com.growtalents.dto.response.SessionLesson.SessionLessonResponseDTO;
import com.growtalents.model.ClassSession;
import com.growtalents.model.Lesson;
import com.growtalents.model.SessionLesson;

public class SessionLessonMapper {
    public static SessionLesson toEntity(SessionLessonCreateRequestDTO dto, String generatedId, ClassSession session, Lesson lesson) {
        SessionLesson sl = new SessionLesson();
        sl.setId(generatedId);
        sl.setSession(session);
        sl.setLesson(lesson);
        sl.setNote(dto.getNote());
        return sl;
    }

    public static SessionLessonResponseDTO toResponseDTO(SessionLesson sl) {
        return SessionLessonResponseDTO.builder()
                .id(sl.getId())
                .sessionId(sl.getSession() != null ? sl.getSession().getSessionId() : null)
                .lessonId(sl.getLesson() != null ? sl.getLesson().getLessonId() : null)
                .note(sl.getNote())
                .build();
    }
}

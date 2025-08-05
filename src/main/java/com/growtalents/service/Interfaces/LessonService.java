package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Lesson.LessonCreateRequestDTO;
import com.growtalents.dto.response.Lesson.LessonResponseDTO;

import java.util.List;

public interface LessonService {
    public List<LessonResponseDTO> getAllLessons(String chapterId);
    public LessonResponseDTO createLesson(LessonCreateRequestDTO dto);
}

package com.growtalents.mapper;

import com.growtalents.dto.request.Lesson.LessonCreateRequestDTO;
import com.growtalents.dto.response.Lesson.LessonResponseDTO;
import com.growtalents.model.Chapter;
import com.growtalents.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonMapper {
    public Lesson toEntity(LessonCreateRequestDTO dto, String generateId, Chapter chapter) {
        return Lesson.builder()
                .lessonId(generateId)
                .chapter(chapter)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
    public LessonResponseDTO toResponseDTO(Lesson lesson) {
        return LessonResponseDTO.builder()
                .lessonId(lesson.getLessonId())
                .title(lesson.getTitle())
                .content(lesson.getContent())
//                .chapter(lesson.getChapter())
                .build();
    }
    public List<LessonResponseDTO> toResponseDTO(List<Lesson> lessons) {
        return lessons.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}

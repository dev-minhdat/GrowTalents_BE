package com.growtalents.mapper;

import com.growtalents.dto.request.Chapter.ChapterCreateRequestDTO;
import com.growtalents.dto.response.Chapter.ChapterResponseDTO;
import com.growtalents.model.Chapter;
import com.growtalents.model.Syllabus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChapterMapper {
    public Chapter toEntity(ChapterCreateRequestDTO dto, String generateId, Syllabus syllabus) {
        return Chapter.builder()
                .chapterId(generateId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .syllabus(syllabus)
                .build();
    }
    public ChapterResponseDTO toResponseDTO(Chapter entity) {
        return ChapterResponseDTO.builder()
                .chapterId(entity.getChapterId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .syllabusId(entity.getSyllabus().getSyllabusId())
                .build();
    }
    public List<ChapterResponseDTO> toResponseDTO(List<Chapter> chapters) {
        return chapters.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}

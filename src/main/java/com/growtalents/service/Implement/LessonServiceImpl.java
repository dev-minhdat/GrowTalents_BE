package com.growtalents.service.Implement;

import com.growtalents.dto.request.Lesson.LessonCreateRequestDTO;
import com.growtalents.dto.response.Lesson.LessonResponseDTO;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.LessonMapper;
import com.growtalents.model.Chapter;
import com.growtalents.model.Lesson;
import com.growtalents.repository.ChapterRepository;
import com.growtalents.repository.LessonRepository;
import com.growtalents.service.Interfaces.LessonService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;
    private final IdSequenceService idSequenceService;
    @Override
    public List<LessonResponseDTO> getAllLessons(String chapterId) {
        List<Lesson> lessons = lessonRepository.findByChapter_ChapterId(chapterId);
//        if (lessons.isEmpty()) {
//            throw new ResourceNotFoundException("Lesson not found by chapter id: " + chapterId);
//        }
        return lessonMapper.toResponseDTO(lessons);
    }

    @Override
    public LessonResponseDTO createLesson(LessonCreateRequestDTO dto) {
        Chapter chapter = chapterRepository.findById(dto.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));

        // Sinh index
        int index = idSequenceService.getNextIndex("Lesson");
        // Sinh ID theo định dạng
        String lessonId = IdGenerator.generateLessonId(index);

        Lesson lesson = Lesson.builder()
                .lessonId(lessonId)
                .chapter(chapter)
                .title(dto.getTitle())
                .build();
        lessonRepository.save(lesson);
        return lessonMapper.toResponseDTO(lesson);
    }
}

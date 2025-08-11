package com.growtalents.service.Implement;

import com.growtalents.dto.request.Chapter.ChapterCreateRequestDTO;
import com.growtalents.dto.response.Chapter.ChapterResponseDTO;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.ChapterMapper;
import com.growtalents.model.Chapter;
import com.growtalents.model.Syllabus;
import com.growtalents.repository.ChapterRepository;
import com.growtalents.repository.SyllabusRepository;
import com.growtalents.service.Interfaces.ChapterService;
import com.growtalents.service.id.IdSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final SyllabusRepository syllabusRepository;
    private final ChapterMapper chapterMapper;
    private final IdSequenceService idSequenceService;
    @Override
    public List<ChapterResponseDTO> getChapters(String syllabusId) {
        List<Chapter> chapters = chapterRepository.findBySyllabus_SyllabusId(syllabusId);
//        if (chapters.isEmpty()) {
//            throw new ResourceNotFoundException("No chapters found for syllabus id: " + syllabusId);
//        }
        return chapterMapper.toResponseDTO(chapters);
    }

    @Override
    public ChapterResponseDTO createChapter(ChapterCreateRequestDTO dto) {
        Syllabus syllabus = syllabusRepository.findById(dto.getSyllabusId())
                .orElseThrow(() -> new ResourceNotFoundException("Syllabus not found for id: " + dto.getSyllabusId()));
        // Sinh index
        int index = idSequenceService.getNextIndex("Chapter");
        // Sinh ID theo định dạng
        String chapterId = IdGenerator.generateChapterId(index);

        Chapter chapter = Chapter.builder()
                .syllabus(syllabus)
                .description(dto.getDescription())
                .title(dto.getTitle())
                .chapterId(chapterId)
                .build();
        chapterRepository.save(chapter);
        return chapterMapper.toResponseDTO(chapter);
    }
}

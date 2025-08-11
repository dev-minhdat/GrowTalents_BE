package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Chapter.ChapterCreateRequestDTO;
import com.growtalents.dto.response.Chapter.ChapterResponseDTO;
import com.growtalents.model.Chapter;

import java.util.List;

public interface ChapterService {
    public List<ChapterResponseDTO> getChapters(String syllabusId);
    public ChapterResponseDTO createChapter(ChapterCreateRequestDTO dto);
}

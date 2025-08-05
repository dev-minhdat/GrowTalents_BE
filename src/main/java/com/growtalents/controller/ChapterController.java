package com.growtalents.controller;

import com.growtalents.dto.request.Chapter.ChapterCreateRequestDTO;
import com.growtalents.dto.response.Chapter.ChapterResponseDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.service.Interfaces.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    // POST /api/chapters
    @PostMapping
    public ResponseEntity<GlobalResponse<ChapterResponseDTO>> createChapter(
            @RequestBody ChapterCreateRequestDTO dto) {
        ChapterResponseDTO response = chapterService.createChapter(dto);
        return ResponseEntity.ok(GlobalResponse.success("Chapter created", response));
    }

    // GET /api/chapters?syllabusId=SYL_2025_MATH_001
    @GetMapping
    public ResponseEntity<GlobalResponse<List<ChapterResponseDTO>>> getChapterBySyllabusId(
            @RequestParam String syllabusId) {
        List<ChapterResponseDTO> response = chapterService.getChapters(syllabusId);
        return ResponseEntity.ok(GlobalResponse.success("Chapter found", response));
    }
}

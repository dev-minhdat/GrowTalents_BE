package com.growtalents.controller;

import com.growtalents.dto.request.Lesson.LessonCreateRequestDTO;
import com.growtalents.dto.response.GlobalResponse;
import com.growtalents.dto.response.Lesson.LessonResponseDTO;
import com.growtalents.service.Interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;

    // GET /api/lessons/{chapterId}
    @GetMapping("/{chapterId}")
    public ResponseEntity<GlobalResponse<List<LessonResponseDTO>>> getLessons(@PathVariable String chapterId) {
        List<LessonResponseDTO> lessons = lessonService.getAllLessons(chapterId);
        return ResponseEntity.ok(GlobalResponse.success(lessons));
    }

    // POST /api/lessons
    @PostMapping
    public ResponseEntity<GlobalResponse<LessonResponseDTO>> createLesson(@RequestBody LessonCreateRequestDTO dto) {
        LessonResponseDTO lesson = lessonService.createLesson(dto);
        return ResponseEntity.ok(GlobalResponse.success("Lesson created successfully", lesson));
    }
}

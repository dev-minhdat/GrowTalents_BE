package com.growtalents.mapper;

import com.growtalents.dto.request.Syllabus.SyllabusCreateRequestDTO;
import com.growtalents.dto.response.Syllabus.SyllabusResponseDTO;
import com.growtalents.model.Course;
import com.growtalents.model.Syllabus;
import org.springframework.stereotype.Component;

@Component
public class SyllabusMapper {
    public Syllabus toEntity(SyllabusCreateRequestDTO dto, String generateId, Course course) {
        return Syllabus.builder()
                .syllabusId(generateId)
                .course(course)
                .title(dto.getTitle())
                .build();
    }
    public SyllabusResponseDTO toResponseDTO(Syllabus entity) {
        return SyllabusResponseDTO.builder()
                .syllabusId(entity.getSyllabusId())
                .title(entity.getTitle())
                .courseId(entity.getCourse().getCourseId())
                .build();
    }
}

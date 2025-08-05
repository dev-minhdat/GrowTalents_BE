package com.growtalents.mapper;

import com.growtalents.dto.request.Course.CourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public Course toEntity (CourseCreateRequestDTO dto, String generateId) {
        return Course.builder()
                .courseId(generateId)
                .description(dto.getDescription())
                .type(dto.getType())
                .duration(dto.getDuration())
                .name(dto.getNameCourse())
                .status(dto.getStatus())
                .tuitionFee(dto.getTuitionFee())
                .build();
    }
    public CourseResponseDTO toResponseDTO (Course entity) {
        return CourseResponseDTO.builder()
                .courseId(entity.getCourseId())
                .nameCourse(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .duration(entity.getDuration())
                .status(entity.getStatus())
                .tuitionFee(entity.getTuitionFee())
                .build();
    }
}

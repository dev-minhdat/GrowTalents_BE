package com.growtalents.mapper;

import com.growtalents.dto.request.Course.CourseCreateRequestDTO;
import com.growtalents.dto.response.Course.CourseResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CourseMapper {
    public Course toEntity (CourseCreateRequestDTO dto, String generateId, AppUser admin) {
        return Course.builder()
                .courseId(generateId)
                .description(dto.getDescription())
                .name(dto.getNameCourse())
                .createdAt(LocalDate.now())
                .imageUrl(dto.getImageUrl())
                .createdBy(admin)
                .lastModified(LocalDate.now())
                .tuitionFee(dto.getTuitionFee())
                .duration(dto.getDuration())
                .type(dto.getCourseType())
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
                .imageUrl(entity.getImageUrl())
                .createdBy(entity.getCreatedBy().getUserName())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getLastModified())
                .build();
    }
}

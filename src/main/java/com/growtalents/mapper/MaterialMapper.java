package com.growtalents.mapper;

import com.growtalents.dto.response.Material.MaterialResponseDTO;
import com.growtalents.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {
    public MaterialResponseDTO toDTO(Material m) {
        return MaterialResponseDTO.builder()
                .materialId(m.getMaterialId())
                .courseId(m.getCourse().getCourseId())
                .type(m.getType())
                .content(m.getContent())
                .build();
    }
}

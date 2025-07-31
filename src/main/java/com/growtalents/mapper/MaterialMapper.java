package com.growtalents.mapper;

import com.growtalents.dto.request.Material.MaterialCreateRequestDTO;
import com.growtalents.dto.response.Material.MaterialResponseDTO;
import com.growtalents.model.AppUser;
import com.growtalents.model.Course;
import com.growtalents.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {
    public Material toEntity(MaterialCreateRequestDTO dto, String generateId, AppUser user, Course course) {
        return Material.builder()
                .materialId(generateId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .fileUrl(dto.getFileUrl())
                .uploadedBy(user)
                .course(course)
                .uploadDate(dto.getUploadDate())
                .build();
    }
    public MaterialResponseDTO toResponseDTO(Material entity) {
        return MaterialResponseDTO.builder()
                .materialId(entity.getMaterialId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .fileUrl(entity.getFileUrl())
                .uploadDate(entity.getUploadDate())
                .uploadedByUserId(entity.getUploadedBy().getUserId())
                .uploadedByUserName(entity.getUploadedBy().getUserName())
                .course(entity.getCourse())
                .build();
    }
}

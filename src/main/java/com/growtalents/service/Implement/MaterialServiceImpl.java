package com.growtalents.service.Implement;

import com.growtalents.dto.request.Material.MaterialUpsertRequestDTO;
import com.growtalents.dto.response.Material.MaterialResponseDTO;
import com.growtalents.enums.MaterialType;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.mapper.MaterialMapper;
import com.growtalents.model.Material;
import com.growtalents.repository.CourseRepository;
import com.growtalents.repository.MaterialRepository;
import com.growtalents.service.Interfaces.MaterialService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepo;
    private final CourseRepository courseRepo;
    private final MaterialMapper mapper;

    @Override
    @Transactional
    public MaterialResponseDTO upsert(MaterialUpsertRequestDTO dto) {
        var course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + dto.getCourseId()));

        var material = materialRepo.findByCourse_CourseIdAndType(dto.getCourseId(), dto.getType())
                .orElseGet(() -> Material.builder()
                        .materialId(UUID.randomUUID().toString())
                        .course(course)
                        .type(dto.getType())
                        .build());

        material.setContent(dto.getContent());
        material = materialRepo.save(material);
        return mapper.toDTO(material);
    }

    @Override
    public MaterialResponseDTO getByCourseAndType(String courseId, MaterialType type) {
        var m = materialRepo.findByCourse_CourseIdAndType(courseId, type)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found"));
        return mapper.toDTO(m);
    }

    @Override
    @Transactional
    public void deleteByCourseAndType(String courseId, MaterialType type) {
        if (!materialRepo.existsByCourse_CourseIdAndType(courseId, type)) {
            throw new ResourceNotFoundException("Material not found");
        }
        materialRepo.deleteByCourse_CourseIdAndType(courseId, type);
    }
}

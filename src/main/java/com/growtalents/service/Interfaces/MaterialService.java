package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Material.MaterialUpsertRequestDTO;
import com.growtalents.dto.response.Material.MaterialResponseDTO;
import com.growtalents.enums.MaterialType;

public interface MaterialService {
    MaterialResponseDTO upsert(MaterialUpsertRequestDTO dto);               // tạo/cập nhật 1 component
    MaterialResponseDTO getByCourseAndType(String courseId, MaterialType type);
    void deleteByCourseAndType(String courseId, MaterialType type);
}

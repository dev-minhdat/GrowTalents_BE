package com.growtalents.mapper;

import com.growtalents.dto.request.AcademicYear.AcademicYearCreateRequestDTO;
import com.growtalents.dto.response.AcademicYear.AcademicYearResponseDTO;
import com.growtalents.model.AcademicYear;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AcademicYearMapper {
    public AcademicYear toEntity (AcademicYearCreateRequestDTO dto) {
        return AcademicYear.builder()
                .year(dto.getAcademicYear())
                .build();
    }
    public AcademicYearResponseDTO toResponseDTO (AcademicYear entity) {
        return AcademicYearResponseDTO.builder()
                .academicYearId(entity.getId())
                .academicYear(entity.getYear())
                .build();
    }
    public List<AcademicYearResponseDTO> toResponseDTO (List<AcademicYear> entities) {
        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}

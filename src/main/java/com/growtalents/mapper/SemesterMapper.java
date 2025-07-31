package com.growtalents.mapper;

import com.growtalents.dto.request.Semester.SemesterCreateRequestDTO;
import com.growtalents.dto.response.Semester.SemesterResponseDTO;
import com.growtalents.model.Semester;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper {
    public static Semester toEntity(SemesterCreateRequestDTO dto, String generatedId) {
        Semester semester = new Semester();
        semester.setSemesterId(generatedId);
        semester.setName(dto.getName());
        semester.setStartDate(dto.getStartDate() != null ? java.time.LocalDate.parse(dto.getStartDate()) : null);
        semester.setEndDate(dto.getEndDate() != null ? java.time.LocalDate.parse(dto.getEndDate()) : null);
        semester.setStatus(dto.getStatus() != null ? com.growtalents.enums.SemesterStatus.valueOf(dto.getStatus()) : null);
        return semester;
    }

    public static SemesterResponseDTO toResponseDTO(Semester semester) {
        return SemesterResponseDTO.builder()
                .semesterId(semester.getSemesterId())
                .name(semester.getName())
                .startDate(semester.getStartDate() != null ? semester.getStartDate().toString() : null)
                .endDate(semester.getEndDate() != null ? semester.getEndDate().toString() : null)
                .status(semester.getStatus() != null ? semester.getStatus().name() : null)
                .build();
    }
}

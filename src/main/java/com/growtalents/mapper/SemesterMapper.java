package com.growtalents.mapper;

import com.growtalents.dto.request.Semester.SemesterCreateRequestDTO;
import com.growtalents.dto.response.Semester.SemesterResponseDTO;
import com.growtalents.model.AcademicYear;
import com.growtalents.model.Semester;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper {
    public  Semester toEntity(SemesterCreateRequestDTO dto, AcademicYear year, String generatedId) {
       return Semester.builder()
               .semesterId(generatedId)
               .name(dto.getName())
               .startDate(dto.getStartDate())
               .endDate(dto.getEndDate())
               .academicYear(year)
               // status xy ly o service
               .build();
    }

    public  SemesterResponseDTO toResponseDTO(Semester semester) {
        return SemesterResponseDTO.builder()
                .semesterId(semester.getSemesterId())
                .name(semester.getName())
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .Year(semester.getAcademicYear().getYear())
                .status(semester.getStatus())
                .build();
    }
}

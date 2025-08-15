package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.AcademicYear.AcademicYearCreateRequestDTO;
import com.growtalents.dto.response.AcademicYear.AcademicYearResponseDTO;

import java.util.List;

public interface AcademicYearService {
    void createAcademicYear(AcademicYearCreateRequestDTO dto);
    List<AcademicYearResponseDTO> getAllAcademicYears();
}

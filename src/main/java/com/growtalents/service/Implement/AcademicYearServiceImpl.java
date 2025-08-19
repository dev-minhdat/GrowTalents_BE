package com.growtalents.service.Implement;

import com.growtalents.dto.request.AcademicYear.AcademicYearCreateRequestDTO;
import com.growtalents.dto.response.AcademicYear.AcademicYearResponseDTO;
import com.growtalents.mapper.AcademicYearMapper;
import com.growtalents.model.AcademicYear;
import com.growtalents.repository.AcademicYearRepository;
import com.growtalents.service.Interfaces.AcademicYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;
    @Override
    public void createAcademicYear(AcademicYearCreateRequestDTO dto) {
        if (dto.getAcademicYear() == null) {
            throw new IllegalArgumentException("Năm học không được để trống");
        }
        if (academicYearRepository.existsByYear(dto.getAcademicYear())) {
            throw new IllegalArgumentException("Năm học đã tồn tại");
        }
        AcademicYear year = academicYearMapper.toEntity(dto);
        academicYearRepository.save(year);
    }


    @Override
    public List<AcademicYearResponseDTO> getAllAcademicYears() {
        List<AcademicYear> academicYears = academicYearRepository.findAll(Sort.by(Sort.Direction.DESC, "year"));

        return academicYearMapper.toResponseDTO(academicYears);
    }
}

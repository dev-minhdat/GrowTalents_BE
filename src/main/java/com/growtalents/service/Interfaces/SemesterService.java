package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Semester.SemesterCreateRequestDTO;
import com.growtalents.dto.response.Semester.SemesterResponseDTO;
import com.growtalents.model.Semester;

import java.util.List;

public interface SemesterService {
    void createSemester(SemesterCreateRequestDTO dto);
    List<SemesterResponseDTO> getSemesterByYear(int year);
    List<SemesterResponseDTO> getAllSemesters();
}

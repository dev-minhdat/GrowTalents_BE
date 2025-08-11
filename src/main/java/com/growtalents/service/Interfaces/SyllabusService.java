package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Syllabus.SyllabusCreateRequestDTO;
import com.growtalents.dto.response.Syllabus.SyllabusResponseDTO;

public interface SyllabusService {
    public SyllabusResponseDTO getSyllabus(String courseId);
    public SyllabusResponseDTO createSyllabus(SyllabusCreateRequestDTO requestDTO);
}

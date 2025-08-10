package com.growtalents.service.Implement;

import com.growtalents.dto.request.Grade.GradeUpdateRequestDTO;
import com.growtalents.enums.PerformanceLevel;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.mapper.GradeMapper;
import com.growtalents.model.Grade;
import com.growtalents.repository.GradeRepository;
import com.growtalents.service.Interfaces.GradeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    @Transactional
    @Override
    public void updateScore(GradeUpdateRequestDTO dto) {
        Grade grade = gradeRepository.findById(dto.getGradeId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy gradeId: " + dto.getGradeId()));

        grade.setScore(dto.getScore());
        grade.setComment(dto.getComment());
        grade.setPerformanceLevel(evaluatePerformanceLevel(dto.getScore()));

        gradeRepository.save(grade);
    }

    private PerformanceLevel evaluatePerformanceLevel(Float score) {
        if (score >= 9.0f) return PerformanceLevel.EXCELLENT;
        else if (score >= 8.0f) return PerformanceLevel.GOOD;
        else if (score >= 6.5f) return PerformanceLevel.FAIR;
        else return PerformanceLevel.AVERAGE;
    }
}

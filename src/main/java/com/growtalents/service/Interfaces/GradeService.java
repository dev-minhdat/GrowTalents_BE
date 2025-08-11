package com.growtalents.service.Interfaces;

import com.growtalents.dto.request.Grade.GradeUpdateRequestDTO;

public interface GradeService {
    public void updateScore(GradeUpdateRequestDTO dto);
}

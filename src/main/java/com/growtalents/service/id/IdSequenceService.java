package com.growtalents.service.id;

import com.growtalents.model.IdSequence;
import com.growtalents.repository.IdSequenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdSequenceService {
    private final IdSequenceRepository idSequenceRepository;

    @Autowired
    public IdSequenceService(IdSequenceRepository idSequenceRepository) {
        this.idSequenceRepository = idSequenceRepository;
    }

    @Transactional
    public int getNextIndex(String entityName) {
        IdSequence sequence = idSequenceRepository.findById(entityName)
                .orElse(IdSequence.builder()
                        .entityName(entityName)
                        .currentIndex(0)
                        .build());

        sequence.setCurrentIndex(sequence.getCurrentIndex() + 1);
        idSequenceRepository.save(sequence);
        return sequence.getCurrentIndex();
    }
}


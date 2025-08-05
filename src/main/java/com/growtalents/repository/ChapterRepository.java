package com.growtalents.repository;

import com.growtalents.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String> {
    List<Chapter> findBySyllabus_SyllabusId(String syllabusId);

}

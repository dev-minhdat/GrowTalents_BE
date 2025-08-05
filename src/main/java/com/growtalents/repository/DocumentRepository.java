package com.growtalents.repository;

import com.growtalents.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document,String> {
    List<Document> findByLesson_LessonId(String lessonId);
}

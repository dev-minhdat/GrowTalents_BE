package com.growtalents.repository;

import com.growtalents.model.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus,String> {
    Syllabus findByCourse_CourseId(String courseId);

}

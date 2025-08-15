package com.growtalents.repository;

import com.growtalents.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
    List<Semester> findByAcademicYear_Year(int year);
}

package com.growtalents.repository;

import com.growtalents.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Integer> {
    boolean existsByYear(Integer year);

}

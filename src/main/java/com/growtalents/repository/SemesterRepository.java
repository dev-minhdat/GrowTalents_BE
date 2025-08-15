
package com.growtalents.repository;

import com.growtalents.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
    
    // Method from master branch
    List<Semester> findByAcademicYear_Year(int year);
    
    // Methods from TuDat branch (for student controller functionality)
    @Query("SELECT DISTINCT YEAR(s.startDate) FROM Semester s ORDER BY YEAR(s.startDate) DESC")
    List<Integer> findDistinctYears();
    
    @Query("SELECT s FROM Semester s WHERE YEAR(s.startDate) = :year ORDER BY s.name")
    List<Semester> findByYearOrderBySemesterName(@Param("year") Integer year);
}
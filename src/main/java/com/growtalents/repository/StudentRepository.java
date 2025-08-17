package com.growtalents.repository;

import com.growtalents.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    @Query("SELECT s FROM Student s WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Student> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByEmail(String email);
    
    // Find by AppUser
    Optional<Student> findByAppUser_UserId(String appUserId);
    boolean existsByAppUser_UserId(String appUserId);
}

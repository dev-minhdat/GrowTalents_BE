package com.growtalents.repository;

import com.growtalents.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    
    @Query("SELECT t FROM Teacher t WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(t.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(t.specialization) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Teacher> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByEmail(String email);
    
    // Find by AppUser
    Optional<Teacher> findByAppUser_UserId(String appUserId);
    boolean existsByAppUser_UserId(String appUserId);
}

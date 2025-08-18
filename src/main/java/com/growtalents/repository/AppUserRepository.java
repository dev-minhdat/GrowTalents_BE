package com.growtalents.repository;

import com.growtalents.enums.UserRole;
import com.growtalents.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    List<AppUser> findAllByUserRole(UserRole role);
    boolean existsByUserEmail(String userEmail);

    // Phân trang và tìm kiếm cho teachers
    @Query("SELECT u FROM AppUser u WHERE u.userRole = :role AND " +
           "(:keyword IS NULL OR :keyword = '' OR " +
           "LOWER(u.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.userEmail) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.specialization) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<AppUser> findByUserRoleWithKeyword(@Param("role") UserRole role, @Param("keyword") String keyword, Pageable pageable);

    Optional<AppUser> findByUserRole(UserRole role);
    Optional<AppUser> findByUserEmailOrAccountName(String email, String accountName);
}
